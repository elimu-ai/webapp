package ai.elimu.dao;

import java.util.List;
import ai.elimu.model.content.multimedia.Video;

import org.springframework.dao.DataAccessException;

import ai.elimu.model.enums.Locale;

public interface VideoDao extends GenericDao<Video> {
	
    Video read(String title, Locale locale) throws DataAccessException;

    List<Video> readAllOrdered(Locale locale) throws DataAccessException;
    
    Long readCount(Locale locale) throws DataAccessException;
}
