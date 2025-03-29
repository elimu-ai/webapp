package ai.elimu.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import ai.elimu.entity.content.Syllable;

public interface SyllableDao extends GenericDao<Syllable> {
    
    Syllable readByText(String text) throws DataAccessException;
    
    List<Syllable> readAllOrdered() throws DataAccessException;
    
    List<Syllable> readAllOrderedByUsage() throws DataAccessException;
}
