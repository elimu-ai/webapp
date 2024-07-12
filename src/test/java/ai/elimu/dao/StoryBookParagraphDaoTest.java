package ai.elimu.dao;

import ai.elimu.model.content.StoryBookParagraph;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringJUnitConfig(locations = {
    "file:src/main/webapp/WEB-INF/spring/applicationContext.xml",
    "file:src/main/webapp/WEB-INF/spring/applicationContext-jpa.xml"
})
public class StoryBookParagraphDaoTest {
    
    @Autowired
    private StoryBookParagraphDao storyBookParagraphDao;
    
    @Test
    public void testReadAllContainingWord_apostrophe() {
        String wordText = "its";
        List<StoryBookParagraph> storyBookParagraphs = storyBookParagraphDao.readAllContainingWord(wordText);
        assertTrue(storyBookParagraphs.isEmpty());
        
        wordText = "it's";
        storyBookParagraphs = storyBookParagraphDao.readAllContainingWord(wordText);
        assertTrue(storyBookParagraphs.isEmpty());
    }
}
