package ai.elimu.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import ai.elimu.entity.analytics.LetterSoundLearningEvent;

public interface LetterSoundLearningEventDao extends GenericDao<LetterSoundLearningEvent> {
    
    public List<LetterSoundLearningEvent> readAll(String androidId) throws DataAccessException;
}
