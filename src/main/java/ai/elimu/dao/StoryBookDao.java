package ai.elimu.dao;

import java.util.List;
import ai.elimu.model.content.StoryBook;

import ai.elimu.model.enums.Locale;

import org.springframework.dao.DataAccessException;

public interface StoryBookDao extends GenericDao<StoryBook> {
	
    StoryBook readByTitle(Locale locale, String title) throws DataAccessException;

    List<StoryBook> readAllOrdered(Locale locale) throws DataAccessException;
    
    Long readCount(Locale locale) throws DataAccessException;
}
