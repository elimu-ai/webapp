package ai.elimu.logic.converters;

import org.apache.commons.lang.StringUtils;
import ai.elimu.dao.AudioDao;
import ai.elimu.entity.content.multimedia.Audio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

public class StringToAudioConverter implements Converter<String, Audio> {

    @Autowired
    private AudioDao audioDao;
    
    /**
     * Convert Audio id to Audio entity
     */
    public Audio convert(String id) {
        if (StringUtils.isBlank(id)) {
            return null;
        } else {
            Long audioId = Long.parseLong(id);
            Audio word = audioDao.read(audioId);
            return word;
        }
    }
}
