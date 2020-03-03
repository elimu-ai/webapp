package ai.elimu.dao;

import java.util.List;
import ai.elimu.model.content.Syllable;

import ai.elimu.model.enums.Language;

import org.springframework.dao.DataAccessException;

public interface SyllableDao extends GenericDao<Syllable> {
    
    Syllable readByText(Language language, String text) throws DataAccessException;
    
    List<Syllable> readAllOrdered(Language language) throws DataAccessException;
    
    List<Syllable> readAllOrderedByUsage(Language language) throws DataAccessException;
    
    Long readCount(Language language) throws DataAccessException;
}
