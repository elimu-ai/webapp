package ai.elimu.rest.v2.service;

import ai.elimu.dao.StoryBookChapterDao;
import ai.elimu.dao.StoryBookDao;
import ai.elimu.dao.StoryBookParagraphDao;
import ai.elimu.entity.content.StoryBook;
import ai.elimu.entity.content.StoryBookChapter;
import ai.elimu.entity.content.StoryBookParagraph;
import ai.elimu.model.v2.gson.content.StoryBookChapterGson;
import ai.elimu.model.v2.gson.content.StoryBookGson;
import ai.elimu.model.v2.gson.content.StoryBookParagraphGson;
import ai.elimu.rest.v2.JpaToGsonConverter;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * Used as layer between Controllers and DAOs in order to enable usage of caching.
 * <p/>
 * Spring caching feature works over AOP proxies, thus internal calls to cached methods don't work. That's why this intermediate service is used. See https://stackoverflow.com/a/48168762
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class StoryBooksJsonService {

  private final StoryBookDao storyBookDao;

  private final StoryBookChapterDao storyBookChapterDao;

  private final StoryBookParagraphDao storyBookParagraphDao;

  @Cacheable("storyBooks")
  public JSONArray getStoryBooksJSONArray() {
    log.info("getStoryBooksJSONArray");

    Date dateStart = new Date();

    JSONArray storyBooksJsonArray = new JSONArray();
    for (StoryBook storyBook : storyBookDao.readAllOrdered()) {
      StoryBookGson storyBookGson = JpaToGsonConverter.getStoryBookGson(storyBook);

      // Add chapters
      List<StoryBookChapterGson> storyBookChapterGsons = new ArrayList<>();
      for (StoryBookChapter storyBookChapter : storyBookChapterDao.readAll(storyBook)) {
        StoryBookChapterGson storyBookChapterGson = JpaToGsonConverter.getStoryBookChapterGson(storyBookChapter);

        // Add paragraphs
        List<StoryBookParagraphGson> storyBookParagraphGsons = new ArrayList<>();
        for (StoryBookParagraph storyBookParagraph : storyBookParagraphDao.readAll(storyBookChapter)) {
          StoryBookParagraphGson storyBookParagraphGson = JpaToGsonConverter.getStoryBookParagraphGson(storyBookParagraph);
          storyBookParagraphGsons.add(storyBookParagraphGson);
        }
        storyBookChapterGson.setStoryBookParagraphs(storyBookParagraphGsons);

        storyBookChapterGsons.add(storyBookChapterGson);
      }
      storyBookGson.setStoryBookChapters(storyBookChapterGsons);

      String json = new Gson().toJson(storyBookGson);
      storyBooksJsonArray.put(new JSONObject(json));
    }

    Date dateEnd = new Date();
    log.info("getStoryBooksJSONArray duration: " + (dateEnd.getTime() - dateStart.getTime()) + " ms");

    return storyBooksJsonArray;
  }

  @CacheEvict("storyBooks")
  public void refreshStoryBooksJSONArray() {
    log.info("refreshStoryBooksJSONArray");
  }
}
