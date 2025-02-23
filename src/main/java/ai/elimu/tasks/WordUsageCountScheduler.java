package ai.elimu.tasks;

import ai.elimu.dao.StoryBookChapterDao;
import ai.elimu.dao.StoryBookDao;
import ai.elimu.dao.StoryBookParagraphDao;
import ai.elimu.dao.WordDao;
import ai.elimu.model.content.StoryBook;
import ai.elimu.model.content.StoryBookChapter;
import ai.elimu.model.content.StoryBookParagraph;
import ai.elimu.model.content.Word;
import ai.elimu.model.v2.enums.Language;
import ai.elimu.util.ConfigHelper;
import ai.elimu.util.WordFrequencyHelper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * Iterates all StoryBooks and calculates the frequency of each word. Does not separate words with differing upper-case and lower-case letters.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class WordUsageCountScheduler {

  private final WordDao wordDao;

  private final StoryBookDao storyBookDao;

  private final StoryBookChapterDao storyBookChapterDao;

  private final StoryBookParagraphDao storyBookParagraphDao;

  @Scheduled(cron = "00 00 06 * * *") // At 06:00 every day
  public synchronized void execute() {
    log.info("execute");

    log.info("Calculating usage count for Words");

    Map<String, Integer> wordFrequencyMap = new HashMap<>();

    Language language = Language.valueOf(ConfigHelper.getProperty("content.language"));

    List<StoryBook> storyBooks = storyBookDao.readAllOrdered();
    log.info("storyBooks.size(): " + storyBooks.size());
    for (StoryBook storyBook : storyBooks) {
      log.debug("storyBook.getTitle(): " + storyBook.getTitle());

      List<String> paragraphs = new ArrayList<>();
      List<StoryBookChapter> storyBookChapters = storyBookChapterDao.readAll(storyBook);
      for (StoryBookChapter storyBookChapter : storyBookChapters) {
        List<StoryBookParagraph> storyBookParagraphs = storyBookParagraphDao.readAll(storyBookChapter);
        for (StoryBookParagraph storyBookParagraph : storyBookParagraphs) {
          paragraphs.add(storyBookParagraph.getOriginalText());
        }
      }

      Map<String, Integer> wordFrequencyMapForBook = WordFrequencyHelper.getWordFrequency(paragraphs, language);
      wordFrequencyMapForBook.keySet().forEach(word -> wordFrequencyMap.put(word, wordFrequencyMap.getOrDefault(word, 0) + wordFrequencyMapForBook.get(word)));
    }

    for (String word : wordFrequencyMap.keySet()) {
      log.info("word: \"" + word + "\"");
      Word existingWord = wordDao.readByText(word);
      if (existingWord != null) {
        existingWord.setUsageCount(wordFrequencyMap.get(word));

        // Temporary fix for "jakarta.validation.ConstraintViolationException"
        if (existingWord.getLetterSounds().isEmpty()) {
          log.warn("Letter-sound correspondences not yet added. Skipping usage count update for word...");
          continue;
        }

        wordDao.update(existingWord);
      }
    }

    log.info("execute complete");
  }
}
