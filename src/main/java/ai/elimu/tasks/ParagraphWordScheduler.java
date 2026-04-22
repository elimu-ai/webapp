package ai.elimu.tasks;

import ai.elimu.dao.StoryBookParagraphDao;
import ai.elimu.dao.WordDao;
import ai.elimu.entity.content.StoryBookParagraph;
import ai.elimu.entity.content.Word;
import ai.elimu.model.v2.enums.Language;
import ai.elimu.rest.v2.service.StoryBooksJsonService;
import ai.elimu.util.ConfigHelper;
import ai.elimu.util.DiscordHelper;
import ai.elimu.util.DiscordHelper.Channel;
import ai.elimu.util.WordExtractionHelper;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * Iterates all {@link StoryBookParagraph}s and looks for {@link Word} matches in the paragraph's original text.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ParagraphWordScheduler {

  private final StoryBookParagraphDao storyBookParagraphDao;

  private final WordDao wordDao;

  private final StoryBooksJsonService storyBooksJsonService;

  @Scheduled(cron = "00 00 * * * *") // Every hour
  public synchronized void execute() {
    log.info("execute");

    try {
      Language language = Language.valueOf(ConfigHelper.getProperty("content.language"));

      List<StoryBookParagraph> storyBookParagraphs = storyBookParagraphDao.readAll();
      log.info("storyBookParagraphs.size(): " + storyBookParagraphs.size());
      for (StoryBookParagraph storyBookParagraph : storyBookParagraphs) {
        log.debug("storyBookParagraph.getId(): " + storyBookParagraph.getId());

        List<String> wordsInOriginalText = WordExtractionHelper.getWords(storyBookParagraph.getOriginalText(), language);
        log.debug("wordsInOriginalText.size(): " + wordsInOriginalText.size());

        // Look for matches of existing Words in the paragraph's original text
        List<Word> words = new ArrayList<>();
        for (String wordInOriginalText : wordsInOriginalText) {
          log.debug("wordInOriginalText: \"" + wordInOriginalText + "\"");
          wordInOriginalText = wordInOriginalText.toLowerCase();
          log.debug("wordInOriginalText (lower-case): \"" + wordInOriginalText + "\"");
          Word word = wordDao.readByText(wordInOriginalText);
          log.debug("word: " + word);
          words.add(word);
        }
        log.info("words.size(): " + words.size());
        storyBookParagraph.setWords(words);

        // Update the paragraph's list of Words in the database
        storyBookParagraphDao.update(storyBookParagraph);
      }

      // Refresh REST API cache
      storyBooksJsonService.refreshStoryBooksJSONArray();
    } catch (Exception e) {
      log.error("Error in scheduled task:", e);
      DiscordHelper.postToChannel(Channel.CONTENT, "Error in " + getClass().getSimpleName() + ":`" + e.getClass() + ": " + e.getMessage() + "`");
    }

    log.info("execute complete");
  }
}
