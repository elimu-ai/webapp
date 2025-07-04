package ai.elimu.dao;

import java.util.Calendar;
import java.util.List;

import org.springframework.dao.DataAccessException;

import ai.elimu.entity.analytics.NumberAssessmentEvent;
import ai.elimu.model.v2.enums.analytics.research.ExperimentGroup;
import ai.elimu.model.v2.enums.analytics.research.ResearchExperiment;

public interface NumberAssessmentEventDao extends GenericDao<NumberAssessmentEvent> {

    NumberAssessmentEvent read(Calendar timestamp, String androidId, String packageName) throws DataAccessException;
    
    List<NumberAssessmentEvent> readAll(String androidId) throws DataAccessException;

    List<NumberAssessmentEvent> readAll(ResearchExperiment researchExperiment, ExperimentGroup experimentGroup) throws DataAccessException;
}
