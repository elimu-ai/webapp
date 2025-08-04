package ai.elimu.dao;

import java.util.Calendar;
import java.util.List;
import org.springframework.dao.DataAccessException;

import ai.elimu.entity.analytics.StoryBookLearningEvent;
import ai.elimu.entity.content.StoryBook;
import ai.elimu.model.v2.enums.analytics.research.ExperimentGroup;
import ai.elimu.model.v2.enums.analytics.research.ResearchExperiment;

public interface StoryBookLearningEventDao extends GenericDao<StoryBookLearningEvent> {
    
    StoryBookLearningEvent read(Calendar time, String androidId, String packageName) throws DataAccessException;

    List<StoryBookLearningEvent> readAllOrderedByTime() throws DataAccessException;

    List<StoryBookLearningEvent> readAll(String androidId) throws DataAccessException;

    List<StoryBookLearningEvent> readAll(ResearchExperiment researchExperiment, ExperimentGroup experimentGroup) throws DataAccessException;

    List<StoryBookLearningEvent> readAll(StoryBook storyBook) throws DataAccessException;
}
