package ai.elimu.dao;

import java.util.List;
import ai.elimu.model.content.multimedia.Audio;

import org.springframework.dao.DataAccessException;

public interface AudioDao extends GenericDao<Audio> {
	
    Audio readByTitle(String title) throws DataAccessException;
    
    Audio readByTranscription(String transcription) throws DataAccessException;

    List<Audio> readAllOrdered() throws DataAccessException;
}
