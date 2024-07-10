package ai.elimu.dao;

import java.util.List;
import ai.elimu.entity.content.Syllable;

import org.springframework.dao.DataAccessException;

public interface SyllableDao extends GenericDao<Syllable> {
    
    Syllable readByText(String text) throws DataAccessException;
    
    List<Syllable> readAllOrdered() throws DataAccessException;
    
    List<Syllable> readAllOrderedByUsage() throws DataAccessException;
}
