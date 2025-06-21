package ai.elimu.dao;

import java.util.Calendar;
import java.util.List;

import org.springframework.dao.DataAccessException;

import ai.elimu.entity.analytics.WordAssessmentEvent;
import ai.elimu.model.v2.enums.analytics.research.ExperimentGroup;
import ai.elimu.model.v2.enums.analytics.research.ResearchExperiment;

public interface WordAssessmentEventDao extends GenericDao<WordAssessmentEvent> {

    WordAssessmentEvent read(Calendar timestamp, String androidId, String packageName) throws DataAccessException;
    
    List<WordAssessmentEvent> readAll(String androidId) throws DataAccessException;

    List<WordAssessmentEvent> readAll(ResearchExperiment researchExperiment, ExperimentGroup experimentGroup) throws DataAccessException;
}
