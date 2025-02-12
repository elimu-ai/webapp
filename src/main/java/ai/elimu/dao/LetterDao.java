package ai.elimu.dao;

import java.util.List;
import ai.elimu.model.content.Letter;

import org.springframework.dao.DataAccessException;

public interface LetterDao extends GenericDao<Letter> {
    
    Letter readByText(String text) throws DataAccessException;
    
    List<Letter> readAllOrdered() throws DataAccessException;
    
    List<Letter> readAllOrderedByUsage() throws DataAccessException;
    
    List<Letter> readAllOrderedById() throws DataAccessException;
}
