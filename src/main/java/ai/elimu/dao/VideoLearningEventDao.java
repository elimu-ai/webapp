package ai.elimu.dao;

import ai.elimu.model.analytics.VideoLearningEvent;
import java.util.Calendar;
import org.springframework.dao.DataAccessException;

public interface VideoLearningEventDao extends GenericDao<VideoLearningEvent> {
    
    VideoLearningEvent read(Calendar timestamp, String androidId, String packageName, String videoTitle) throws DataAccessException;
}
