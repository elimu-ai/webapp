package ai.elimu.dao;

import java.util.List;
import ai.elimu.model.content.multimedia.Audio;

import org.springframework.dao.DataAccessException;

import ai.elimu.model.enums.Locale;

public interface AudioDao extends GenericDao<Audio> {
	
    Audio read(String transcription, Locale locale) throws DataAccessException;

    List<Audio> readAllOrdered(Locale locale) throws DataAccessException;
    
    Long readCount(Locale locale) throws DataAccessException;
}
