package ai.elimu.tasks;

import ai.elimu.dao.LetterDao;
import ai.elimu.dao.LetterSoundDao;
import ai.elimu.entity.content.Letter;
import ai.elimu.entity.content.LetterSound;
import ai.elimu.util.DiscordHelper;
import ai.elimu.util.DiscordHelper.Channel;

import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * Iterates all letter-sounds and calculates the frequency of each letter.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class LetterUsageCountScheduler {

  private final LetterDao letterDao;

  private final LetterSoundDao letterSoundDao;

  @Scheduled(cron = "00 20 06 * * *") // At 06:20 every day
  public synchronized void execute() {
    log.info("execute");

    try {
      // <ID, frequency>
      Map<Long, Integer> frequencyMap = new HashMap<>();
      
      // Calculate the frequency of each letter
      for (LetterSound letterSound : letterSoundDao.readAll()) {
        for (Letter letter : letterSound.getLetters()) {
          frequencyMap.put(letter.getId(), frequencyMap.getOrDefault(letter.getId(), 0) + letterSound.getUsageCount());
        }
      }

      // Update the values previously stored in the database
      for (Letter letter : letterDao.readAll()) {
        letter.setUsageCount(frequencyMap.getOrDefault(letter.getId(), 0));
        letterDao.update(letter);
      }
    } catch (Exception e) {
      log.error("Error in scheduled task:", e);
      DiscordHelper.postToChannel(Channel.CONTENT, "Error in " + getClass().getSimpleName() + ":`" + e.getClass() + ": " + e.getMessage() + "`");
    }

    log.info("execute complete");
  }
}
