package ai.elimu.logic.converters;

import org.apache.commons.lang.StringUtils;
import ai.elimu.dao.LetterDao;
import ai.elimu.entity.content.Letter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

public class StringToLetterConverter implements Converter<String, Letter> {

    @Autowired
    private LetterDao letterDao;
    
    /**
     * Convert Letter id to Letter entity
     */
    public Letter convert(String id) {
        if (StringUtils.isBlank(id)) {
            return null;
        } else {
            Long letterId = Long.parseLong(id);
            Letter letter = letterDao.read(letterId);
            return letter;
        }
    }
}
