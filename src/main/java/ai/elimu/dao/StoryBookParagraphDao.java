package ai.elimu.dao;

import java.util.List;
import ai.elimu.entity.content.StoryBookChapter;
import ai.elimu.entity.content.StoryBookParagraph;

import org.springframework.dao.DataAccessException;

public interface StoryBookParagraphDao extends GenericDao<StoryBookParagraph> {
	
    List<StoryBookParagraph> readAll(StoryBookChapter storyBookChapter) throws DataAccessException;
    
    List<StoryBookParagraph> readAllContainingWord(String wordText) throws DataAccessException;
}
