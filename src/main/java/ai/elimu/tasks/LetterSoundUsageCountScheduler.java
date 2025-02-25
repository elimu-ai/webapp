package ai.elimu.tasks;

import ai.elimu.dao.LetterSoundDao;
import ai.elimu.dao.WordDao;
import ai.elimu.model.content.Letter;
import ai.elimu.model.content.LetterSound;
import ai.elimu.model.content.Sound;
import ai.elimu.model.content.Word;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class LetterSoundUsageCountScheduler {

  private final WordDao wordDao;

  private final LetterSoundDao letterSoundDao;

  @Scheduled(cron = "00 15 06 * * *") // At 06:15 every day
  public synchronized void execute() {
    log.info("execute");

    log.info("Calculating usage count for LetterSounds");

    // <id, usageCount>
    Map<Long, Integer> letterSoundFrequencyMap = new HashMap<>();

    List<Word> words = wordDao.readAll();
    log.info("words.size(): " + words.size());
    for (Word word : words) {
      log.debug("word.getText(): " + word.getText());
      for (LetterSound letterSound : word.getLetterSounds()) {
        letterSoundFrequencyMap.put(letterSound.getId(),
            letterSoundFrequencyMap.getOrDefault(letterSound.getId(), 0) + word.getUsageCount());
      }
    }

    // Update the values previously stored in the database
    for (LetterSound letterSound : letterSoundDao.readAll()) {
      log.debug("letterSound.getId(): " + letterSound.getId());
      log.debug("letterSound Letters: \"" + letterSound.getLetters().stream().map(Letter::getText).collect(Collectors.joining()) + "\"");
      log.debug("letterSound Sounds: /" + letterSound.getSounds().stream().map(Sound::getValueIpa).collect(Collectors.joining()) + "/");
      log.debug("letterSound.getUsageCount() (before update): " + letterSound.getUsageCount());

      int newUsageCount = 0;
      if (letterSoundFrequencyMap.containsKey(letterSound.getId())) {
        newUsageCount = letterSoundFrequencyMap.get(letterSound.getId());
      }
      log.info("newUsageCount: " + newUsageCount);

      letterSound.setUsageCount(newUsageCount);
      letterSoundDao.update(letterSound);
      log.info("letterSound.getUsageCount() (after update): " + letterSound.getUsageCount());
    }

    log.info("execute complete");
  }
}
