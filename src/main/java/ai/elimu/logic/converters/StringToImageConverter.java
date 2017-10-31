package ai.elimu.logic.converters;

import org.apache.commons.lang.StringUtils;
import ai.elimu.dao.ImageDao;
import ai.elimu.model.content.multimedia.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

public class StringToImageConverter implements Converter<String, Image> {

    @Autowired
    private ImageDao imageDao;
    
    /**
     * Convert Image id to Image entity
     */
    public Image convert(String id) {
        if (StringUtils.isBlank(id)) {
            return null;
        } else {
            Long imageId = Long.parseLong(id);
            Image word = imageDao.read(imageId);
            return word;
        }
    }
}
