package ai.elimu.tasks;

import ai.elimu.dao.StoryBookParagraphDao;
import ai.elimu.dao.WordDao;
import ai.elimu.entity.content.StoryBookParagraph;
import ai.elimu.entity.content.Word;

import java.util.HashMap;
import java.util.List;
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

    // <ID, frequency>
    Map<Long, Integer> frequencyMap = new HashMap<>();

    List<Word> words = wordDao.readAll();
    log.info("words.size(): " + words.size());

    List<StoryBookParagraph> storyBookParagraphs = storyBookParagraphDao.readAll();
    log.info("storyBookParagraphs.size(): " + storyBookParagraphs.size());

    // Calculate the frequency of each word
    for (Word word : words) {
      frequencyMap.put(word.getId(), 0);
      for (StoryBookParagraph storyBookParagraph : storyBookParagraphs) {
        for (Word wordInParagraph : storyBookParagraph.getWords()) {
          if (wordInParagraph.getId() == word.getId()) {
            frequencyMap.put(word.getId(), frequencyMap.get(word.getId()) + 1);
          }
        }
      }
    }

    // Update the values previously stored in the database
    for (Word word : words) {
      word.setUsageCount(frequencyMap.get(word.getId()));
      wordDao.update(word);
    }

    log.info("execute complete");
  }
}
