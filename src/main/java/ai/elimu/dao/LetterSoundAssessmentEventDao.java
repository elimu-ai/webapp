package ai.elimu.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import ai.elimu.entity.analytics.LetterSoundAssessmentEvent;

public interface LetterSoundAssessmentEventDao extends GenericDao<LetterSoundAssessmentEvent> {
    
    List<LetterSoundAssessmentEvent> readAll(String androidId) throws DataAccessException;
}
