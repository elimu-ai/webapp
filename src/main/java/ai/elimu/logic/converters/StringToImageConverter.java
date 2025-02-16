package ai.elimu.logic.converters;

import ai.elimu.dao.ImageDao;
import ai.elimu.model.content.multimedia.Image;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.core.convert.converter.Converter;

@AllArgsConstructor
public class StringToImageConverter implements Converter<String, Image> {

  private final ImageDao imageDao;

  /**
   * Convert Image id to Image entity
   */
  public Image convert(String id) {
    if (StringUtils.isBlank(id)) {
      return null;
    } else {
      Long imageId = Long.parseLong(id);
      return imageDao.read(imageId);
    }
  }
}
