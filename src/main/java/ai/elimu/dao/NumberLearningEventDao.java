package ai.elimu.dao;

import java.util.Calendar;
import java.util.List;

import org.springframework.dao.DataAccessException;

import ai.elimu.entity.analytics.NumberLearningEvent;
import ai.elimu.model.v2.enums.analytics.research.ExperimentGroup;
import ai.elimu.model.v2.enums.analytics.research.ResearchExperiment;

public interface NumberLearningEventDao extends GenericDao<NumberLearningEvent> {
    
    NumberLearningEvent read(Calendar time, String androidId, String packageName) throws DataAccessException;

    List<NumberLearningEvent> readAll(String androidId) throws DataAccessException;

    List<NumberLearningEvent> readAll(ResearchExperiment researchExperiment, ExperimentGroup experimentGroup) throws DataAccessException;
}
