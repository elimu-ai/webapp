package ai.elimu.tasks;

import ai.elimu.dao.LetterSoundDao;
import ai.elimu.dao.WordDao;
import ai.elimu.entity.content.LetterSound;
import ai.elimu.entity.content.Word;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * Iterates all words and calculates the frequency of each letter-sound.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class LetterSoundUsageCountScheduler {

  private final WordDao wordDao;

  private final LetterSoundDao letterSoundDao;

  @Scheduled(cron = "00 10 06 * * *") // At 06:10 every day
  public synchronized void execute() {
    log.info("execute");

    // <ID, frequency>
    Map<Long, Integer> frequencyMap = new HashMap<>();

    List<LetterSound> letterSounds = letterSoundDao.readAll();
    log.info("letterSounds.size(): " + letterSounds.size());

    List<Word> words = wordDao.readAll();
    log.info("words.size(): " + words.size());

    // Calculate the frequency of each letter-sound
    for (LetterSound letterSound : letterSounds) {
      frequencyMap.put(letterSound.getId(), 0);
      for (Word word : words) {
        for (LetterSound letterSoundInWord : word.getLetterSounds()) {
          if (letterSoundInWord.getId() == letterSound.getId()) {
            frequencyMap.put(letterSound.getId(), frequencyMap.get(letterSound.getId()) + 1);
          }
        }
      }
    }

    // Update the values previously stored in the database
    for (LetterSound letterSound : letterSounds) {
      letterSound.setUsageCount(frequencyMap.get(letterSound.getId()));
      letterSoundDao.update(letterSound);
    }

    log.info("execute complete");
  }
}
