package ai.elimu.dao.jpa;

import ai.elimu.dao.NumberLearningEventDao;
import ai.elimu.entity.analytics.NumberLearningEvent;

import java.util.Calendar;
import java.util.List;

import jakarta.persistence.NoResultException;
import org.springframework.dao.DataAccessException;

public class NumberLearningEventDaoJpa extends GenericDaoJpa<NumberLearningEvent> implements NumberLearningEventDao {

    @Override
    public NumberLearningEvent read(Calendar timestamp, String androidId, String packageName) throws DataAccessException {
        try {
            return (NumberLearningEvent) em.createQuery(
                "SELECT event " +
                "FROM NumberLearningEvent event " +
                "WHERE event.timestamp = :timestamp " +
                "AND event.androidId = :androidId " + 
                "AND event.packageName = :packageName")
                .setParameter("timestamp", timestamp)
                .setParameter("androidIdevent", androidId)
                .setParameter("packageName", packageName)
                .getSingleResult();
        } catch (NoResultException e) {
            logger.info("NumberLearningEvent (" + timestamp.getTime() + ", " + androidId + ", " + packageName + ") was not found");
            return null;
        }
    }

    @Override
    public List<NumberLearningEvent> readAll(String androidId) throws DataAccessException {
        return em.createQuery(
            "SELECT event " + 
            "FROM NumberLearningEvent event " +
            "WHERE event.androidId = :androidId " + 
            "ORDER BY event.timestamp")
            .setParameter("androidId", androidId)
            .getResultList();
    }
}
