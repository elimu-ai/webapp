package ai.elimu.logic.converters;

import ai.elimu.dao.LetterDao;
import ai.elimu.entity.content.Letter;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.core.convert.converter.Converter;

@AllArgsConstructor
public class StringToLetterConverter implements Converter<String, Letter> {

  private final LetterDao letterDao;

  /**
   * Convert Letter id to Letter entity
   */
  public Letter convert(String id) {
    if (StringUtils.isBlank(id)) {
      return null;
    } else {
      Long letterId = Long.parseLong(id);
      return letterDao.read(letterId);
    }
  }
}
