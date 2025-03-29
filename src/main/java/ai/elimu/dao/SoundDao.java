package ai.elimu.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import ai.elimu.entity.content.Sound;

public interface SoundDao extends GenericDao<Sound> {
    
    Sound readByValueIpa(String value) throws DataAccessException;
    
    Sound readByValueSampa(String value) throws DataAccessException;

    List<Sound> readAllOrdered() throws DataAccessException;
    
    List<Sound> readAllOrderedByIpaValueCharacterLength() throws DataAccessException;
    
    List<Sound> readAllOrderedByUsage() throws DataAccessException;

    List<Sound> readAllOrderedById() throws DataAccessException;
}
