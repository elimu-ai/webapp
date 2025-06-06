package ai.elimu.tasks;

import ai.elimu.dao.SoundDao;
import ai.elimu.dao.WordDao;
import ai.elimu.entity.content.LetterSound;
import ai.elimu.entity.content.Sound;
import ai.elimu.entity.content.Word;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * Iterates all Words and calculates the frequency of Sounds, based on the Word's frequency in StoryBooks.
 * <p/>
 * For this to work, the frequency of each {@link Word} must have been calculated and stored previously (see {@link WordUsageCountScheduler} and {@link LetterSoundUsageCountScheduler}).
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SoundUsageCountScheduler {

  private final SoundDao soundDao;

  private final WordDao wordDao;

  @Scheduled(cron = "00 30 06 * * *") // At 06:30 every day
  public synchronized void execute() {
    log.info("execute");

    log.info("Calculating usage count of Sounds");

    // Long = Sound ID
    // Integer = Usage count
    Map<Long, Integer> soundFrequencyMap = new HashMap<>();

    // Summarize the usage count of each Word's Sounds based on the LetterSound's
    // usage count (see LetterSoundUsageCountScheduler).
    List<Word> words = wordDao.readAllOrdered();
    log.info("words.size(): " + words.size());
    for (Word word : words) {
      for (LetterSound letterSound : word.getLetterSounds()) {
        for (Sound sound : letterSound.getSounds()) {
          soundFrequencyMap.put(sound.getId(), soundFrequencyMap.getOrDefault(sound.getId(), 0) + letterSound.getUsageCount());
        }
      }
    }
    // Update each Sound's usage count in the database
    for (Long soundId : soundFrequencyMap.keySet()) {
      Sound sound = soundDao.read(soundId);
      sound.setUsageCount(soundFrequencyMap.get(soundId));
      soundDao.update(sound);
    }

    log.info("execute complete");
  }
}
