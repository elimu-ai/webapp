package ai.elimu.tasks;

import ai.elimu.dao.StoryBookParagraphDao;
import ai.elimu.dao.WordDao;
import ai.elimu.entity.content.StoryBookParagraph;
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
 * Iterates all storybook paragraphs and calculates the frequency of each word.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class WordUsageCountScheduler {

  private final WordDao wordDao;

  private final StoryBookParagraphDao storyBookParagraphDao;

  @Scheduled(cron = "00 00 06 * * *") // At 06:00 every day
  public synchronized void execute() {
    log.info("execute");

    try {
      // <ID, frequency>
      Map<Long, Integer> frequencyMap = new HashMap<>();

      // Calculate the frequency of each word
      for (StoryBookParagraph storyBookParagraph : storyBookParagraphDao.readAll()) {
        for (Word word : storyBookParagraph.getWords()) {
          frequencyMap.put(word.getId(), frequencyMap.getOrDefault(word.getId(), 0) + 1);
        }
      }

      // Update the values previously stored in the database
      for (Word word : wordDao.readAll()) {
        word.setUsageCount(frequencyMap.getOrDefault(word.getId(), 0));
        wordDao.update(word);
      }
    } catch (Exception e) {
      log.error("Error in scheduled task:", e);
      DiscordHelper.postToChannel(Channel.CONTENT, "Error in " + getClass().getSimpleName() + ":`" + e.getClass() + ": " + e.getMessage() + "`");
    }

    log.info("execute complete");
  }
}
