package ai.elimu.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import ai.elimu.entity.content.Number;

public interface NumberDao extends GenericDao<Number> {
    
    Number readByValue(Integer value) throws DataAccessException;

    List<Number> readAllOrdered() throws DataAccessException;

    List<Number> readAllOrderedById() throws DataAccessException;
}
