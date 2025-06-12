package ai.elimu.dao.jpa;

import java.util.Calendar;
import java.util.List;

import org.springframework.dao.DataAccessException;

import ai.elimu.dao.LetterSoundAssessmentEventDao;
import ai.elimu.entity.analytics.LetterSoundAssessmentEvent;
import jakarta.persistence.NoResultException;

public class LetterSoundAssessmentEventDaoJpa extends GenericDaoJpa<LetterSoundAssessmentEvent> implements LetterSoundAssessmentEventDao {

    @Override
    public LetterSoundAssessmentEvent read(Calendar timestamp, String androidId, String packageName) throws DataAccessException {
        try {
            return (LetterSoundAssessmentEvent) em.createQuery(
                "SELECT event " +
                "FROM LetterSoundAssessmentEvent event " +
                "WHERE event.timestamp = :timestamp " +
                "AND event.androidId = :androidId " + 
                "AND event.packageName = :packageName")
                .setParameter("timestamp", timestamp)
                .setParameter("androidId", androidId)
                .setParameter("packageName", packageName)
                .getSingleResult();
        } catch (NoResultException e) {
            logger.info("LetterSoundAssessmentEvent (" + timestamp.getTime() + ", \"" + androidId + "\", \"" + packageName + "\") was not found");
            return null;
        }
    }

    @Override
    public List<LetterSoundAssessmentEvent> readAll(String androidId) throws DataAccessException {
        return em.createQuery(
            "SELECT event " + 
            "FROM LetterSoundAssessmentEvent event " +
            "WHERE event.androidId = :androidId " + 
            "ORDER BY event.timestamp")
            .setParameter("androidId", androidId)
            .getResultList();
    }
}
