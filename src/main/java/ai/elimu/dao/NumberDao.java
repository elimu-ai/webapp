package ai.elimu.dao;

import java.util.List;

import ai.elimu.model.content.Number;

import org.springframework.dao.DataAccessException;

public interface NumberDao extends GenericDao<Number> {
    
    Number readByValue(Integer value) throws DataAccessException;

    List<Number> readAllOrdered() throws DataAccessException;
    
    List<Number> readAllOrderedById() throws DataAccessException;
}
