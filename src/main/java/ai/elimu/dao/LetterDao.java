package ai.elimu.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import ai.elimu.entity.content.Letter;

public interface LetterDao extends GenericDao<Letter> {
    
    Letter readByText(String text) throws DataAccessException;
    
    List<Letter> readAllOrdered() throws DataAccessException;
    
    List<Letter> readAllOrderedByUsage() throws DataAccessException;

    List<Letter> readAllOrderedById() throws DataAccessException;
}
