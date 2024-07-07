package ai.elimu.logic.converters;

import org.apache.commons.lang.StringUtils;
import ai.elimu.entity.content.Sound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import ai.elimu.dao.SoundDao;

public class StringToSoundConverter implements Converter<String, Sound> {

    @Autowired
    private SoundDao soundDao;
    
    /**
     * Convert Sound id to Sound entity
     */
    public Sound convert(String id) {
        if (StringUtils.isBlank(id)) {
            return null;
        } else {
            Long soundId = Long.parseLong(id);
            Sound sound = soundDao.read(soundId);
            return sound;
        }
    }
}
