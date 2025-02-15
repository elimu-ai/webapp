package ai.elimu.tasks;

import ai.elimu.dao.StoryBookChapterDao;
import ai.elimu.dao.StoryBookDao;
import ai.elimu.dao.StoryBookParagraphDao;
import ai.elimu.dao.SyllableDao;
import ai.elimu.dao.WordDao;
import ai.elimu.model.content.StoryBook;
import ai.elimu.model.content.StoryBookChapter;
import ai.elimu.model.content.StoryBookParagraph;
import ai.elimu.model.content.Syllable;
import ai.elimu.model.content.Word;
import ai.elimu.model.v2.enums.Language;
import ai.elimu.util.ConfigHelper;
import ai.elimu.util.SyllableFrequencyHelper;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * Iterates all StoryBooks and calculates the frequency of each syllable. Lower-case and upper-case variants are considered as two different syllables, e.g. 'a' and 'A'.
 */
@Service
@RequiredArgsConstructor
public class SyllableUsageCountScheduler {

  private Logger logger = LogManager.getLogger();

  private final SyllableDao syllableDao;

  private final StoryBookDao storyBookDao;

  private final StoryBookChapterDao storyBookChapterDao;

  private final StoryBookParagraphDao storyBookParagraphDao;

  private final WordDao wordDao;

  @Scheduled(cron = "00 30 07 * * *") // At 07:30 every morning
  public synchronized void execute() {
    logger.info("execute");

    logger.info("Calculating usage count for Syllables");

    Map<String, Integer> syllableFrequencyMap = new HashMap<>();

    Language language = Language.valueOf(ConfigHelper.getProperty("content.language"));

    List<StoryBook> storyBooks = storyBookDao.readAllOrdered();
    logger.info("storyBooks.size(): " + storyBooks.size());
    for (StoryBook storyBook : storyBooks) {
      logger.debug("storyBook.getTitle(): " + storyBook.getTitle());

      List<String> paragraphs = new ArrayList<>();
      List<StoryBookChapter> storyBookChapters = storyBookChapterDao.readAll(storyBook);
      for (StoryBookChapter storyBookChapter : storyBookChapters) {
        List<StoryBookParagraph> storyBookParagraphs = storyBookParagraphDao.readAll(storyBookChapter);
        for (StoryBookParagraph storyBookParagraph : storyBookParagraphs) {
          paragraphs.add(storyBookParagraph.getOriginalText());
        }
      }

      Map<String, Integer> syllableFrequencyMapForBook = SyllableFrequencyHelper.getSyllableFrequency(paragraphs, language);
      syllableFrequencyMapForBook.keySet()
          .forEach(syllableText -> syllableFrequencyMap.put(syllableText, syllableFrequencyMap.getOrDefault(syllableText, 0) + syllableFrequencyMapForBook.get(syllableText)));
    }

    logger.info("syllableFrequencyMap: " + syllableFrequencyMap);

    for (String syllableText : syllableFrequencyMap.keySet()) {
      // Skip syllables that are actual words
      // TODO: add logic to Word editing
      Word word = wordDao.readByText(syllableText);
      if (word != null) {
        continue;
      }

      // Skip syllables that are not digrams
      // TODO: add support for trigrams
      if (syllableText.length() != 2) {
        continue;
      }

      Syllable existingSyllable = syllableDao.readByText(syllableText);
      if (existingSyllable == null) {
        Syllable syllable = new Syllable();
        syllable.setTimeLastUpdate(Calendar.getInstance());
        syllable.setText(syllableText);
        syllable.setUsageCount(syllableFrequencyMap.get(syllableText));
        syllableDao.create(syllable);
      } else {
        existingSyllable.setUsageCount(syllableFrequencyMap.get(syllableText));
        syllableDao.update(existingSyllable);
      }
    }

    logger.info("execute complete");
  }
}
