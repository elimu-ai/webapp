package ai.elimu.dao;

import java.util.List;
import org.springframework.dao.DataAccessException;

/**
 * DAO with basic CRUD functionality.
 * 
 * @param <T> The class type
 */
public interface GenericDao<T> {

    void create(T t) throws DataAccessException;

    T read(Long id) throws DataAccessException;

    List<T> readAll() throws DataAccessException;

    void update(T t) throws DataAccessException;

    void delete(T t) throws DataAccessException;
}
