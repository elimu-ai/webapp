package ai.elimu.logic.converters;

import ai.elimu.dao.StoryBookParagraphDao;
import ai.elimu.entity.content.StoryBookParagraph;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.core.convert.converter.Converter;

@AllArgsConstructor
public class StringToStoryBookParagraphConverter implements Converter<String, StoryBookParagraph> {

  private final StoryBookParagraphDao storyBookParagraphDao;

  /**
   * Convert StoryBookParagraph id to StoryBookParagraph entity
   */
  public StoryBookParagraph convert(String id) {
    if (StringUtils.isBlank(id)) {
      return null;
    } else {
      Long storyBookParagraphId = Long.parseLong(id);
      return storyBookParagraphDao.read(storyBookParagraphId);
    }
  }
}
