package ai.elimu.logic.converters;

import ai.elimu.dao.WordDao;
import ai.elimu.entity.content.Word;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.core.convert.converter.Converter;

@AllArgsConstructor
public class StringToWordConverter implements Converter<String, Word> {

  private final WordDao wordDao;

  /**
   * Convert Word id to Word entity
   */
  public Word convert(String id) {
    if (StringUtils.isBlank(id)) {
      return null;
    } else {
      Long wordId = Long.parseLong(id);
      return wordDao.read(wordId);
    }
  }
}
