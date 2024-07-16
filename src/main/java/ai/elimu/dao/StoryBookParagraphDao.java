package ai.elimu.dao;

import java.util.List;
import ai.elimu.model.content.StoryBookChapter;
import ai.elimu.model.content.StoryBookParagraph;

import org.springframework.dao.DataAccessException;

public interface StoryBookParagraphDao extends GenericDao<StoryBookParagraph> {
    
    List<StoryBookParagraph> readAll(StoryBookChapter storyBookChapter) throws DataAccessException;
    
    List<StoryBookParagraph> readAllContainingWord(String wordText) throws DataAccessException;
}
