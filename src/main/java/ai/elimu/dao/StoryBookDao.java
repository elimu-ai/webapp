package ai.elimu.dao;

import java.util.List;
import ai.elimu.model.content.StoryBook;

import ai.elimu.model.enums.Language;
import ai.elimu.model.enums.ReadingLevel;

import org.springframework.dao.DataAccessException;

public interface StoryBookDao extends GenericDao<StoryBook> {
	
    StoryBook readByTitle(Language language, String title) throws DataAccessException;

    List<StoryBook> readAllOrdered(Language language) throws DataAccessException;
    
    List<StoryBook> readAllOrdered(Language language, ReadingLevel readingLevel) throws DataAccessException;
    
    List<StoryBook> readAllUnleveled(Language language) throws DataAccessException;
    
    Long readCount(Language language) throws DataAccessException;
}
