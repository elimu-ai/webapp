package ai.elimu.dao;

import java.util.List;
import ai.elimu.model.content.multimedia.Video;

import org.springframework.dao.DataAccessException;

import ai.elimu.model.enums.Language;

public interface VideoDao extends GenericDao<Video> {
	
    Video read(String title, Language language) throws DataAccessException;

    List<Video> readAllOrdered(Language language) throws DataAccessException;
    
    Long readCount(Language language) throws DataAccessException;
}
