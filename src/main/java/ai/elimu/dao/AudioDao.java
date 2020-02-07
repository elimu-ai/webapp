package ai.elimu.dao;

import java.util.List;
import ai.elimu.model.content.multimedia.Audio;

import org.springframework.dao.DataAccessException;

import ai.elimu.model.enums.Language;

public interface AudioDao extends GenericDao<Audio> {
	
    Audio read(String transcription, Language language) throws DataAccessException;

    List<Audio> readAllOrdered(Language language) throws DataAccessException;
    
    Long readCount(Language language) throws DataAccessException;
}
