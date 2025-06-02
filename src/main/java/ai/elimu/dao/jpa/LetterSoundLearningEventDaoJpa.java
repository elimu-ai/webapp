package ai.elimu.dao.jpa;

import java.util.Calendar;
import java.util.List;

import org.springframework.dao.DataAccessException;

import ai.elimu.dao.LetterSoundLearningEventDao;
import ai.elimu.entity.analytics.LetterSoundLearningEvent;
import jakarta.persistence.NoResultException;

public class LetterSoundLearningEventDaoJpa extends GenericDaoJpa<LetterSoundLearningEvent> implements LetterSoundLearningEventDao {

    @Override
    public LetterSoundLearningEvent read(Calendar timestamp, String androidId, String packageName) throws DataAccessException {
        try {
            return (LetterSoundLearningEvent) em.createQuery(
                "SELECT event " +
                "FROM LetterSoundLearningEvent event " +
                "WHERE event.timestamp = :timestamp " +
                "AND event.androidId = :androidId " + 
                "AND event.packageName = :packageName")
                .setParameter("timestamp", timestamp)
                .setParameter("androidId", androidId)
                .setParameter("packageName", packageName)
                .getSingleResult();
        } catch (NoResultException e) {
            logger.info("LetterSoundLearningEvent (" + timestamp.getTimeInMillis() + ", " + androidId + ", \"" + packageName + "\") was not found");
            return null;
        }
    }
    
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
