package ai.elimu.logic.converters;

import org.apache.commons.lang.StringUtils;
import ai.elimu.dao.StoryBookChapterDao;
import ai.elimu.entity.content.StoryBookChapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

public class StringToStoryBookChapterConverter implements Converter<String, StoryBookChapter> {

    @Autowired
    private StoryBookChapterDao storyBookChapterDao;
    
    /**
     * Convert StoryBookChapter id to StoryBookChapter entity
     */
    public StoryBookChapter convert(String id) {
        if (StringUtils.isBlank(id)) {
            return null;
        } else {
            Long storyBookChapterId = Long.parseLong(id);
            StoryBookChapter storyBookChapter = storyBookChapterDao.read(storyBookChapterId);
            return storyBookChapter;
        }
    }
}
