package ai.elimu.dao;

import ai.elimu.dao.enums.OrderDirection;
import ai.elimu.entity.analytics.VideoLearningEvent;

import java.util.Calendar;
import java.util.List;

import org.springframework.dao.DataAccessException;

public interface VideoLearningEventDao extends GenericDao<VideoLearningEvent> {
    
    VideoLearningEvent read(Calendar timestamp, String androidId, String packageName, String videoTitle) throws DataAccessException;

    List<VideoLearningEvent> readAllOrderedByTimestamp(OrderDirection orderDirection) throws DataAccessException;

    List<VideoLearningEvent> readAll(String androidId) throws DataAccessException;
}
