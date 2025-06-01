package ai.elimu.dao.jpa;

import java.util.Calendar;
import java.util.List;

import org.springframework.dao.DataAccessException;

import ai.elimu.dao.WordAssessmentEventDao;
import ai.elimu.entity.analytics.WordAssessmentEvent;
import jakarta.persistence.NoResultException;

public class WordAssessmentEventDaoJpa extends GenericDaoJpa<WordAssessmentEvent> implements WordAssessmentEventDao {

    @Override
    public WordAssessmentEvent read(Calendar timestamp, String androidId, String packageName) throws DataAccessException {
        try {
            return (WordAssessmentEvent) em.createQuery(
                "SELECT event " +
                "FROM WordAssessmentEvent event " +
                "WHERE event.timestamp = :timestamp " +
                "AND event.androidId = :androidId " + 
                "AND event.packageName = :packageName")
                .setParameter("timestamp", timestamp)
                .setParameter("androidId", androidId)
                .setParameter("packageName", packageName)
                .getSingleResult();
        } catch (NoResultException e) {
            logger.info("WordAssessmentEvent (" + timestamp.getTimeInMillis() + ", " + androidId + ", \"" + packageName + "\") was not found");
            return null;
        }
    }

    @Override
    public List<WordAssessmentEvent> readAll(String androidId) throws DataAccessException {
        return em.createQuery(
            "SELECT event " + 
            "FROM WordAssessmentEvent event " +
            "WHERE event.androidId = :androidId " + 
            "ORDER BY event.id")
            .setParameter("androidId", androidId)
            .getResultList();
    }
}
