package ai.elimu.dao;

import java.util.Calendar;
import java.util.List;

import org.springframework.dao.DataAccessException;

import ai.elimu.entity.analytics.WordLearningEvent;
import ai.elimu.model.v2.enums.analytics.research.ExperimentGroup;
import ai.elimu.model.v2.enums.analytics.research.ResearchExperiment;

public interface WordLearningEventDao extends GenericDao<WordLearningEvent> {
    
    WordLearningEvent read(Calendar time, String androidId, String packageName) throws DataAccessException;

    List<WordLearningEvent> readAll(String androidId) throws DataAccessException;

    List<WordLearningEvent> readAll(ResearchExperiment researchExperiment, ExperimentGroup experimentGroup) throws DataAccessException;
}
