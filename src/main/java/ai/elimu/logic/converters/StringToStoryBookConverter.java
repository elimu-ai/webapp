package ai.elimu.logic.converters;

import org.apache.commons.lang.StringUtils;
import ai.elimu.dao.StoryBookDao;
import ai.elimu.entity.content.StoryBook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

public class StringToStoryBookConverter implements Converter<String, StoryBook> {

    @Autowired
    private StoryBookDao storyBookDao;
    
    /**
     * Convert StoryBook id to StoryBook entity
     */
    public StoryBook convert(String id) {
        if (StringUtils.isBlank(id)) {
            return null;
        } else {
            Long storyBookId = Long.parseLong(id);
            StoryBook storyBook = storyBookDao.read(storyBookId);
            return storyBook;
        }
    }
}
