package ai.elimu.dao;

import java.util.List;
import ai.elimu.model.content.Letter;

import ai.elimu.model.enums.Locale;

import org.springframework.dao.DataAccessException;

public interface LetterDao extends GenericDao<Letter> {
    
    Letter readByText(Locale locale, String text) throws DataAccessException;
    
    List<Letter> readAllOrdered(Locale locale) throws DataAccessException;
    
    Long readCount(Locale locale) throws DataAccessException;
}
