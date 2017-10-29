package ai.elimu.logic.converters;

import org.apache.commons.lang.StringUtils;
import ai.elimu.dao.WordDao;
import ai.elimu.model.content.Word;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

public class StringToWordConverter implements Converter<String, Word> {

    @Autowired
    private WordDao wordDao;
    
    /**
     * Convert Word id to Word entity
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
