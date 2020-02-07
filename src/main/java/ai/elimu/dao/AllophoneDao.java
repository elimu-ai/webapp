package ai.elimu.dao;

import java.util.List;
import ai.elimu.model.content.Allophone;
import ai.elimu.model.enums.Language;

import org.springframework.dao.DataAccessException;

public interface AllophoneDao extends GenericDao<Allophone> {
	
    Allophone readByValueIpa(Language language, String value) throws DataAccessException;
    
    Allophone readByValueSampa(Language language, String value) throws DataAccessException;

    List<Allophone> readAllOrdered(Language language) throws DataAccessException;
    
    List<Allophone> readAllOrderedByUsage(Language language) throws DataAccessException;
}
