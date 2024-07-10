package ai.elimu.dao;

import ai.elimu.entity.admin.Application;
import ai.elimu.entity.analytics.StoryBookLearningEvent;
import ai.elimu.entity.content.StoryBook;
import java.util.Calendar;
import java.util.List;
import org.springframework.dao.DataAccessException;

public interface StoryBookLearningEventDao extends GenericDao<StoryBookLearningEvent> {
    
    List<StoryBookLearningEvent> readAllOrderedByTime() throws DataAccessException;
    
    StoryBookLearningEvent read(Calendar time, String androidId, Application application, StoryBook storyBook) throws DataAccessException;
}
