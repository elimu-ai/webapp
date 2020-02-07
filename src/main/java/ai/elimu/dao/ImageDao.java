package ai.elimu.dao;

import java.util.List;
import ai.elimu.model.content.Word;

import org.springframework.dao.DataAccessException;

import ai.elimu.model.content.multimedia.Image;
import ai.elimu.model.enums.Language;

public interface ImageDao extends GenericDao<Image> {
	
    Image read(String title, Language language) throws DataAccessException;

    List<Image> readAllOrdered(Language language) throws DataAccessException;
    
    /**
     * Fetch all Images that have been labeled by a Word.
     */
    List<Image> readAllLabeled(Word word, Language language) throws DataAccessException;
    
    Long readCount(Language language) throws DataAccessException;
}
