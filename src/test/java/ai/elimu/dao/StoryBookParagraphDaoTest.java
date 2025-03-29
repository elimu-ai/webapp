package ai.elimu.dao;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import ai.elimu.entity.content.StoryBookParagraph;

@SpringJUnitConfig(locations = {
    "file:src/main/webapp/WEB-INF/spring/applicationContext.xml",
    "file:src/main/webapp/WEB-INF/spring/applicationContext-jpa.xml"
})
public class StoryBookParagraphDaoTest {

  private final StoryBookParagraphDao storyBookParagraphDao;

  @Autowired
  public StoryBookParagraphDaoTest(StoryBookParagraphDao storyBookParagraphDao) {
    this.storyBookParagraphDao = storyBookParagraphDao;
  }

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
