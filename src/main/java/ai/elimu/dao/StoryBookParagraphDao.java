package ai.elimu.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import ai.elimu.entity.content.StoryBook;
import ai.elimu.entity.content.StoryBookChapter;
import ai.elimu.entity.content.StoryBookParagraph;

public interface StoryBookParagraphDao extends GenericDao<StoryBookParagraph> {
    
    List<StoryBookParagraph> readAll(StoryBookChapter storyBookChapter) throws DataAccessException;

    List<StoryBookParagraph> readAll(StoryBook storyBook) throws DataAccessException;
    
    List<StoryBookParagraph> readAllContainingWord(String wordText) throws DataAccessException;
}
