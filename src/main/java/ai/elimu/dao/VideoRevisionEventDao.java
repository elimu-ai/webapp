package ai.elimu.dao;

import java.util.List;
import ai.elimu.model.content.multimedia.Video;
import ai.elimu.model.contributor.VideoRevisionEvent;
import org.springframework.dao.DataAccessException;

public interface VideoRevisionEventDao extends GenericDao<VideoRevisionEvent> {
    
    List<VideoRevisionEvent> readAll(Video video) throws DataAccessException;
}
