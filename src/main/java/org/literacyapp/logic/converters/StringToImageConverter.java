package org.literacyapp.logic.converters;

import org.apache.commons.lang.StringUtils;
import org.literacyapp.dao.ImageDao;
import org.literacyapp.model.content.multimedia.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

public class StringToImageConverter implements Converter<String, Image> {

    @Autowired
    private ImageDao imageDao;
    
    /**
     * Convert Image id to Image
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
