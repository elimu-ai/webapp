package ai.elimu.dao;

import java.util.List;

import ai.elimu.model.content.Number;
import ai.elimu.model.enums.Locale;

import org.springframework.dao.DataAccessException;

public interface NumberDao extends GenericDao<Number> {
	
    Number readByValue(Locale locale, Integer value) throws DataAccessException;

    List<Number> readAllOrdered(Locale locale) throws DataAccessException;
    
    Long readCount(Locale locale) throws DataAccessException;
}
