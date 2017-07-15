package ai.elimu.dao;

import java.util.List;
import ai.elimu.model.content.Syllable;

import ai.elimu.model.enums.Locale;

import org.springframework.dao.DataAccessException;

public interface SyllableDao extends GenericDao<Syllable> {
    
    Syllable readByText(Locale locale, String text) throws DataAccessException;
    
    List<Syllable> readAllOrdered(Locale locale) throws DataAccessException;
    
    Long readCount(Locale locale) throws DataAccessException;
}
