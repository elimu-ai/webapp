package ai.elimu.logic.converters;

import ai.elimu.dao.StoryBookDao;
import ai.elimu.entity.content.StoryBook;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.core.convert.converter.Converter;

@AllArgsConstructor
public class StringToStoryBookConverter implements Converter<String, StoryBook> {

  private final StoryBookDao storyBookDao;

  /**
   * Convert StoryBook id to StoryBook entity
   */
  public StoryBook convert(String id) {
    if (StringUtils.isBlank(id)) {
      return null;
    } else {
      Long storyBookId = Long.parseLong(id);
      return storyBookDao.read(storyBookId);
    }
  }
}
