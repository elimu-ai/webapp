package ai.elimu.dao;

import java.util.Calendar;
import java.util.List;

import org.springframework.dao.DataAccessException;

import ai.elimu.entity.analytics.LetterSoundAssessmentEvent;

public interface LetterSoundAssessmentEventDao extends GenericDao<LetterSoundAssessmentEvent> {

    LetterSoundAssessmentEvent read(Calendar timestamp, String androidId, String packageName) throws DataAccessException;
    
    List<LetterSoundAssessmentEvent> readAll(String androidId) throws DataAccessException;
}
