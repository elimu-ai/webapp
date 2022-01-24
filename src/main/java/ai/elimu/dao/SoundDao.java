package ai.elimu.dao;

import java.util.List;
import ai.elimu.model.content.Allophone;

import org.springframework.dao.DataAccessException;

public interface SoundDao extends GenericDao<Allophone> {
	
    Allophone readByValueIpa(String value) throws DataAccessException;
    
    Allophone readByValueSampa(String value) throws DataAccessException;

    List<Allophone> readAllOrdered() throws DataAccessException;
    
    List<Allophone> readAllOrderedByIpaValueCharacterLength() throws DataAccessException;
    
    List<Allophone> readAllOrderedByUsage() throws DataAccessException;
}
