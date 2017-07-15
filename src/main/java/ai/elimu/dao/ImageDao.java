package ai.elimu.dao;

import java.util.List;
import ai.elimu.model.content.Word;

import org.springframework.dao.DataAccessException;

import ai.elimu.model.content.multimedia.Image;
import ai.elimu.model.enums.Locale;

public interface ImageDao extends GenericDao<Image> {
	
    Image read(String title, Locale locale) throws DataAccessException;

    List<Image> readAllOrdered(Locale locale) throws DataAccessException;
    
    /**
     * Fetch all Images that have been labeled by a Word.
     */
    List<Image> readAllLabeled(Word word, Locale locale) throws DataAccessException;
    
    Long readCount(Locale locale) throws DataAccessException;
}
