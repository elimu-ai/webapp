package ai.elimu.dao;

import java.util.List;
import ai.elimu.model.content.Letter;

import ai.elimu.model.enums.Language;

import org.springframework.dao.DataAccessException;

public interface LetterDao extends GenericDao<Letter> {
    
    Letter readByText(Language language, String text) throws DataAccessException;
    
    List<Letter> readAllOrdered(Language language) throws DataAccessException;
    
    List<Letter> readAllOrderedByUsage(Language language) throws DataAccessException;
    
    Long readCount(Language language) throws DataAccessException;
}
