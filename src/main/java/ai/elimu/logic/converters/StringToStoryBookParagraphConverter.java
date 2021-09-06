package ai.elimu.logic.converters;

import org.apache.commons.lang.StringUtils;
import ai.elimu.dao.StoryBookParagraphDao;
import ai.elimu.model.content.StoryBookParagraph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

public class StringToStoryBookParagraphConverter implements Converter<String, StoryBookParagraph> {

    @Autowired
    private StoryBookParagraphDao storyBookParagraphDao;
    
    /**
     * Convert StoryBookParagraph id to StoryBookParagraph entity
     */
    public StoryBookParagraph convert(String id) {
        if (StringUtils.isBlank(id)) {
            return null;
        } else {
            Long storyBookParagraphId = Long.parseLong(id);
            StoryBookParagraph storyBookParagraph = storyBookParagraphDao.read(storyBookParagraphId);
            return storyBookParagraph;
        }
    }
}
