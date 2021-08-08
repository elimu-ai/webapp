package ai.elimu.logic.converters;

import org.apache.commons.lang.StringUtils;
import ai.elimu.dao.LetterToAllophoneMappingDao;
import ai.elimu.model.content.LetterToAllophoneMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

public class StringToLetterToAllophoneMappingConverter implements Converter<String, LetterToAllophoneMapping> {

    @Autowired
    private LetterToAllophoneMappingDao letterSoundCorrespondenceDao;
    
    /**
     * Convert LetterToAllophoneMapping id to LetterToAllophoneMapping entity
     */
    public LetterToAllophoneMapping convert(String id) {
        if (StringUtils.isBlank(id)) {
            return null;
        } else {
            Long letterSoundCorrespondenceId = Long.parseLong(id);
            LetterToAllophoneMapping letterSoundCorrespondence = letterSoundCorrespondenceDao.read(letterSoundCorrespondenceId);
            return letterSoundCorrespondence;
        }
    }
}
