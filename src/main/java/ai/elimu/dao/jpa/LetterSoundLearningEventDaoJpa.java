package ai.elimu.dao.jpa;

import java.util.List;

import org.springframework.dao.DataAccessException;

import ai.elimu.dao.LetterSoundLearningEventDao;
import ai.elimu.entity.analytics.LetterSoundLearningEvent;

public class LetterSoundLearningEventDaoJpa extends GenericDaoJpa<LetterSoundLearningEvent> implements LetterSoundLearningEventDao {

    @Override
    public List<LetterSoundLearningEvent> readAll(String androidId) throws DataAccessException {
        return em.createQuery(
            "SELECT event " + 
            "FROM LetterSoundLearningEvent event " +
            "WHERE event.androidId = :androidId " + 
            "ORDER BY event.id")
            .setParameter("androidId", androidId)
            .getResultList();
    }
}
