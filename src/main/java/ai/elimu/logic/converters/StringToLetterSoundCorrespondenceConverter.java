package ai.elimu.logic.converters;

import org.apache.commons.lang.StringUtils;
import ai.elimu.model.content.LetterSoundCorrespondence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import ai.elimu.dao.LetterSoundDao;

public class StringToLetterSoundCorrespondenceConverter implements Converter<String, LetterSoundCorrespondence> {

    @Autowired
    private LetterSoundDao letterSoundDao;
    
    /**
     * Convert LetterSoundCorrespondence id to LetterSoundCorrespondence entity
     */
    public LetterSoundCorrespondence convert(String id) {
        if (StringUtils.isBlank(id)) {
            return null;
        } else {
            Long letterSoundCorrespondenceId = Long.parseLong(id);
            LetterSoundCorrespondence letterSoundCorrespondence = letterSoundDao.read(letterSoundCorrespondenceId);
            return letterSoundCorrespondence;
        }
    }
}
