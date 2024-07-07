package ai.elimu.dao;

import ai.elimu.entity.content.StoryBookParagraph;

import java.util.List;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
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
        assertThat(storyBookParagraphs.size(), is(0));
        
        wordText = "it's";
        storyBookParagraphs = storyBookParagraphDao.readAllContainingWord(wordText);
        assertThat(storyBookParagraphs.size(), is(0));
    }
}
