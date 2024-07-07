package ai.elimu.dao;

import java.util.List;
import ai.elimu.entity.content.multimedia.Video;

import org.springframework.dao.DataAccessException;

public interface VideoDao extends GenericDao<Video> {
	
    Video read(String title) throws DataAccessException;

    List<Video> readAllOrdered() throws DataAccessException;
}
