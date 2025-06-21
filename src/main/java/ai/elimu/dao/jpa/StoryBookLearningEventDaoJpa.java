package ai.elimu.dao.jpa;

import ai.elimu.dao.StoryBookLearningEventDao;
import ai.elimu.entity.analytics.StoryBookLearningEvent;
import ai.elimu.model.v2.enums.analytics.research.ExperimentGroup;
import ai.elimu.model.v2.enums.analytics.research.ResearchExperiment;

import java.util.Calendar;
import java.util.List;
import jakarta.persistence.NoResultException;
import org.springframework.dao.DataAccessException;

public class StoryBookLearningEventDaoJpa extends GenericDaoJpa<StoryBookLearningEvent> implements StoryBookLearningEventDao {

    @Override
    public List<StoryBookLearningEvent> readAllOrderedByTime() throws DataAccessException {
        return em.createQuery(
            "SELECT sble " +
            "FROM StoryBookLearningEvent sble " +
            "ORDER BY sble.timestamp DESC")
            .getResultList();
    }
    
    @Override
    public StoryBookLearningEvent read(Calendar timestamp, String androidId, String packageName) throws DataAccessException {
        try {
            return (StoryBookLearningEvent) em.createQuery(
                "SELECT sble " +
                "FROM StoryBookLearningEvent sble " +
                "WHERE sble.timestamp = :timestamp " +
                "AND sble.androidId = :androidId " + 
                "AND sble.packageName = :packageName")
                .setParameter("timestamp", timestamp)
                .setParameter("androidId", androidId)
                .setParameter("packageName", packageName)
                .getSingleResult();
        } catch (NoResultException e) {
            logger.info("StoryBookLearningEvent (" + timestamp.getTime() + ", " + androidId + ", " + packageName + ") was not found");
            return null;
        }
    }

    @Override
    public List<StoryBookLearningEvent> readAll(String androidId) throws DataAccessException {
        return em.createQuery(
            "SELECT event " + 
            "FROM StoryBookLearningEvent event " +
            "WHERE event.androidId = :androidId " + 
            "ORDER BY event.timestamp")
            .setParameter("androidId", androidId)
            .getResultList();
    }

    @Override
    public List<StoryBookLearningEvent> readAll(ResearchExperiment researchExperiment, ExperimentGroup experimentGroup)
            throws DataAccessException {
        return em.createQuery(
            "SELECT event " + 
            "FROM StoryBookLearningEvent event " +
            "WHERE event.researchExperiment = :researchExperiment " + 
            "AND event.experimentGroup = :experimentGroup " +
            "ORDER BY event.timestamp")
            .setParameter("researchExperiment", researchExperiment)
            .setParameter("experimentGroup", experimentGroup)
            .getResultList();
    }   
}
