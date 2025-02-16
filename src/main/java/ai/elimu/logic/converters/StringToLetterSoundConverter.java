package ai.elimu.logic.converters;

import ai.elimu.dao.LetterSoundDao;
import ai.elimu.model.content.LetterSound;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.core.convert.converter.Converter;

@AllArgsConstructor
public class StringToLetterSoundConverter implements Converter<String, LetterSound> {

  private final LetterSoundDao letterSoundDao;

  /**
   * Convert LetterSound id to LetterSound entity
   */
  public LetterSound convert(String id) {
    if (StringUtils.isBlank(id)) {
      return null;
    } else {
      Long letterSoundId = Long.parseLong(id);
      return letterSoundDao.read(letterSoundId);
    }
  }
}
