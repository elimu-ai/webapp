package ai.elimu.dao;

import java.util.List;
import ai.elimu.model.content.StoryBook;

import ai.elimu.model.enums.ReadingLevel;

import org.springframework.dao.DataAccessException;

public interface StoryBookDao extends GenericDao<StoryBook> {
	
    StoryBook readByTitle(String title) throws DataAccessException;

    List<StoryBook> readAllOrdered() throws DataAccessException;
    
    List<StoryBook> readAllOrdered(ReadingLevel readingLevel) throws DataAccessException;
    
    List<StoryBook> readAllUnleveled() throws DataAccessException;
    
    Long readCount() throws DataAccessException;
}
