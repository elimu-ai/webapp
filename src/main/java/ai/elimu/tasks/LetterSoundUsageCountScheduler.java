package ai.elimu.tasks;

import ai.elimu.dao.LetterSoundDao;
import ai.elimu.dao.WordDao;
import ai.elimu.entity.content.LetterSound;
import ai.elimu.entity.content.Word;
import ai.elimu.util.DiscordHelper;
import ai.elimu.util.DiscordHelper.Channel;

import java.util.HashMap;
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

    try {
      // <ID, frequency>
      Map<Long, Integer> frequencyMap = new HashMap<>();

      // Calculate the frequency of each letter-sound
      for (Word word : wordDao.readAll()) {
        for (LetterSound letterSound : word.getLetterSounds()) {
          frequencyMap.put(letterSound.getId(), frequencyMap.getOrDefault(letterSound.getId(), 0) + word.getUsageCount());
        }
      }

      // Update the values previously stored in the database
      for (LetterSound letterSound : letterSoundDao.readAll()) {
        letterSound.setUsageCount(frequencyMap.getOrDefault(letterSound.getId(), 0));
        letterSoundDao.update(letterSound);
      }
    } catch (Exception e) {
      log.error("Error in scheduled task:", e);
      DiscordHelper.postToChannel(Channel.CONTENT, "Error in " + getClass().getSimpleName() + ":`" + e.getClass() + ": " + e.getMessage() + "`");
    }

    log.info("execute complete");
  }
}
