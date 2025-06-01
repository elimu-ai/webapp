package ai.elimu.dao;

import java.util.Calendar;
import java.util.List;

import org.springframework.dao.DataAccessException;

import ai.elimu.entity.analytics.WordAssessmentEvent;

public interface WordAssessmentEventDao extends GenericDao<WordAssessmentEvent> {

    WordAssessmentEvent read(Calendar timestamp, String androidId, String packageName) throws DataAccessException;
    
    List<WordAssessmentEvent> readAll(String androidId) throws DataAccessException;
}
