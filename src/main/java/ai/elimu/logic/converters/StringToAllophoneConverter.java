package ai.elimu.logic.converters;

import org.apache.commons.lang.StringUtils;
import ai.elimu.model.content.Allophone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import ai.elimu.dao.SoundDao;

public class StringToAllophoneConverter implements Converter<String, Allophone> {

    @Autowired
    private SoundDao soundDao;
    
    /**
     * Convert Sound id to Sound entity
     */
    public Allophone convert(String id) {
        if (StringUtils.isBlank(id)) {
            return null;
        } else {
            Long soundId = Long.parseLong(id);
            Allophone sound = soundDao.read(soundId);
            return sound;
        }
    }
}
