package ai.elimu.dao.jpa;

import ai.elimu.dao.WordLearningEventDao;
import ai.elimu.entity.analytics.WordLearningEvent;

import java.util.Calendar;
import java.util.List;

import jakarta.persistence.NoResultException;
import org.springframework.dao.DataAccessException;

public class WordLearningEventDaoJpa extends GenericDaoJpa<WordLearningEvent> implements WordLearningEventDao {

    @Override
    public WordLearningEvent read(Calendar timestamp, String androidId, String packageName) throws DataAccessException {
        try {
            return (WordLearningEvent) em.createQuery(
                "SELECT wle " +
                "FROM WordLearningEvent wle " +
                "WHERE wle.timestamp = :timestamp " +
                "AND wle.androidId = :androidId " + 
                "AND wle.packageName = :packageName")
                .setParameter("timestamp", timestamp)
                .setParameter("androidId", androidId)
                .setParameter("packageName", packageName)
                .getSingleResult();
        } catch (NoResultException e) {
            logger.info("WordLearningEvent (" + timestamp.getTime() + ", " + androidId + ", " + packageName + ") was not found");
            return null;
        }
    }

    @Override
    public List<WordLearningEvent> readAll(String androidId) throws DataAccessException {
        return em.createQuery(
            "SELECT event " + 
            "FROM WordLearningEvent event " +
            "WHERE event.androidId = :androidId " + 
            "ORDER BY event.timestamp")
            .setParameter("androidId", androidId)
            .getResultList();
    }
}
