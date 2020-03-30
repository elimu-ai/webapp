package ai.elimu.dao;

import java.util.List;

import ai.elimu.model.content.Number;
import ai.elimu.model.enums.Language;

import org.springframework.dao.DataAccessException;

public interface NumberDao extends GenericDao<Number> {
	
    Number readByValue(Language language, Integer value) throws DataAccessException;

    List<Number> readAllOrdered(Language language) throws DataAccessException;
    
    Long readCount(Language language) throws DataAccessException;
}
