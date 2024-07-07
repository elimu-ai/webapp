package ai.elimu.dao;

import java.util.List;
import ai.elimu.entity.content.Sound;

import org.springframework.dao.DataAccessException;

public interface SoundDao extends GenericDao<Sound> {
	
    Sound readByValueIpa(String value) throws DataAccessException;
    
    Sound readByValueSampa(String value) throws DataAccessException;

    List<Sound> readAllOrdered() throws DataAccessException;
    
    List<Sound> readAllOrderedByIpaValueCharacterLength() throws DataAccessException;
    
    List<Sound> readAllOrderedByUsage() throws DataAccessException;
}
