package ai.elimu.tasks;

import ai.elimu.dao.LetterSoundDao;
import ai.elimu.dao.SoundDao;
import ai.elimu.entity.content.LetterSound;
import ai.elimu.entity.content.Sound;
import ai.elimu.util.DiscordHelper;
import ai.elimu.util.DiscordHelper.Channel;

import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * Iterates all letter-sounds and calculates the frequency of each sound.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SoundUsageCountScheduler {

  private final SoundDao soundDao;

  private final LetterSoundDao letterSoundDao;

  @Scheduled(cron = "00 30 06 * * *") // At 06:30 every day
  public synchronized void execute() {
    log.info("execute");

    try {
      // <ID, frequency>
      Map<Long, Integer> frequencyMap = new HashMap<>();

      // Calculate the frequency of each sound
      for (LetterSound letterSound : letterSoundDao.readAll()) {
        for (Sound sound : letterSound.getSounds()) {
          frequencyMap.put(sound.getId(), frequencyMap.getOrDefault(sound.getId(), 0) + letterSound.getUsageCount());
        }
      }

      // Update the values previously stored in the database
      for (Sound sound : soundDao.readAll()) {
        sound.setUsageCount(frequencyMap.getOrDefault(sound.getId(), 0));
        soundDao.update(sound);
      }
    } catch (Exception e) {
      log.error("Error in scheduled task:", e);
      DiscordHelper.postToChannel(Channel.CONTENT, "Error in `" + e.getClass() + ": " + e.getMessage() + "`");
    }

    log.info("execute complete");
  }
}
