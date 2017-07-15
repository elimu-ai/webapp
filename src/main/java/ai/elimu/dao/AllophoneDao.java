package ai.elimu.dao;

import java.util.List;
import ai.elimu.model.content.Allophone;
import ai.elimu.model.enums.Locale;

import org.springframework.dao.DataAccessException;

public interface AllophoneDao extends GenericDao<Allophone> {
	
    Allophone readByValueIpa(Locale locale, String value) throws DataAccessException;
    
    Allophone readByValueSampa(Locale locale, String value) throws DataAccessException;

    List<Allophone> readAllOrdered(Locale locale) throws DataAccessException;
    
    List<Allophone> readAllOrderedByUsage(Locale locale) throws DataAccessException;
}
