package org.literacyapp.logic.converters;

import org.apache.commons.lang.StringUtils;
import org.literacyapp.dao.WordDao;
import org.literacyapp.model.content.Word;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

public class StringToWordConverter implements Converter<String, Word> {

    @Autowired
    private WordDao wordDao;
    
    /**
     * Convert Word id to Word
     */
    public Word convert(String id) {
        if (StringUtils.isBlank(id)) {
            return null;
        } else {
            Long wordId = Long.parseLong(id);
            Word word = wordDao.read(wordId);
            return word;
        }
    }
}
