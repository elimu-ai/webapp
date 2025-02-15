package ai.elimu.logic.converters;

import ai.elimu.dao.StoryBookChapterDao;
import ai.elimu.model.content.StoryBookChapter;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.core.convert.converter.Converter;

@AllArgsConstructor
public class StringToStoryBookChapterConverter implements Converter<String, StoryBookChapter> {

  private final StoryBookChapterDao storyBookChapterDao;

  /**
   * Convert StoryBookChapter id to StoryBookChapter entity
   */
  public StoryBookChapter convert(String id) {
    if (StringUtils.isBlank(id)) {
      return null;
    } else {
      Long storyBookChapterId = Long.parseLong(id);
      return storyBookChapterDao.read(storyBookChapterId);
    }
  }
}
