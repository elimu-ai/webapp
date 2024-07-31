package ai.elimu.logic.converters;

import org.apache.commons.lang.StringUtils;
import ai.elimu.model.content.LetterSound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import ai.elimu.dao.LetterSoundDao;

public class StringToLetterSoundConverter implements Converter<String, LetterSound> {

    @Autowired
    private LetterSoundDao letterSoundDao;
    
    /**
     * Convert LetterSound id to LetterSound entity
     */
    public LetterSound convert(String id) {
        if (StringUtils.isBlank(id)) {
            return null;
        } else {
            Long letterSoundId = Long.parseLong(id);
            LetterSound letterSound = letterSoundDao.read(letterSoundId);
            return letterSound;
        }
    }
}
