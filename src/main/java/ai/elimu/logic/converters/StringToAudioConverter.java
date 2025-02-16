package ai.elimu.logic.converters;

import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringUtils;
import ai.elimu.dao.AudioDao;
import ai.elimu.model.content.multimedia.Audio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

@AllArgsConstructor
public class StringToAudioConverter implements Converter<String, Audio> {

    private final AudioDao audioDao;
    
    /**
     * Convert Audio id to Audio entity
     */
    public Audio convert(String id) {
        if (StringUtils.isBlank(id)) {
            return null;
        } else {
            Long audioId = Long.parseLong(id);
            return audioDao.read(audioId);
        }
    }
}
