package ai.elimu.dao;

import java.util.Calendar;
import java.util.List;

import org.springframework.dao.DataAccessException;

import ai.elimu.entity.analytics.LetterSoundLearningEvent;

public interface LetterSoundLearningEventDao extends GenericDao<LetterSoundLearningEvent> {

    LetterSoundLearningEvent read(Calendar time, String androidId, String packageName) throws DataAccessException;
    
    List<LetterSoundLearningEvent> readAll(String androidId) throws DataAccessException;
}
