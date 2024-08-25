package ai.elimu.dao;

import java.util.List;
import ai.elimu.model.content.Word;

import org.springframework.dao.DataAccessException;

import ai.elimu.model.content.multimedia.Image;

public interface ImageDao extends GenericDao<Image> {
    
    Image read(String title) throws DataAccessException;

    List<Image> readAllOrdered() throws DataAccessException;
    
    /**
     * Fetch all Images that have been labeled by a Word.
     */
    List<Image> readAllLabeled(Word word) throws DataAccessException;
}
