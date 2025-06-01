package ai.elimu.dao;

import java.util.Calendar;
import java.util.List;
import org.springframework.dao.DataAccessException;

import ai.elimu.entity.analytics.StoryBookLearningEvent;
import ai.elimu.entity.content.StoryBook;

public interface StoryBookLearningEventDao extends GenericDao<StoryBookLearningEvent> {
    
    StoryBookLearningEvent read(Calendar time, String androidId, String packageName, StoryBook storyBook) throws DataAccessException;

    List<StoryBookLearningEvent> readAllOrderedByTime() throws DataAccessException;

    List<StoryBookLearningEvent> readAll(String androidId) throws DataAccessException;
}
