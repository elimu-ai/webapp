package ai.elimu.logic.converters;

import ai.elimu.dao.SoundDao;
import ai.elimu.model.content.Sound;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.core.convert.converter.Converter;

@AllArgsConstructor
public class StringToSoundConverter implements Converter<String, Sound> {

  private final SoundDao soundDao;

  /**
   * Convert Sound id to Sound entity
   */
  public Sound convert(String id) {
    if (StringUtils.isBlank(id)) {
      return null;
    } else {
      Long soundId = Long.parseLong(id);
      return soundDao.read(soundId);
    }
  }
}
