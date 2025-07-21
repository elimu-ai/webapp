package ai.elimu.tasks;

import ai.elimu.dao.LetterDao;
import ai.elimu.dao.LetterSoundDao;
import ai.elimu.entity.content.Letter;
import ai.elimu.entity.content.LetterSound;

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

    // <ID, frequency>
    Map<Long, Integer> frequencyMap = new HashMap<>();
    
    // Calculate the frequency of each letter
    for (LetterSound letterSound : letterSoundDao.readAll()) {
      for (Letter letter : letterSound.getLetters()) {
        frequencyMap.put(letter.getId(), frequencyMap.getOrDefault(letter.getId(), 0) + 1);
      }
    }

    // Update the values previously stored in the database
    for (Letter letter : letterDao.readAll()) {
      letter.setUsageCount(frequencyMap.getOrDefault(letter.getId(), 0));
      letterDao.update(letter);
    }

    log.info("execute complete");
  }
}
