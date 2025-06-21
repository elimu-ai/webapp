package ai.elimu.dao.jpa;

import ai.elimu.dao.VideoLearningEventDao;
import ai.elimu.dao.enums.OrderDirection;
import ai.elimu.entity.analytics.VideoLearningEvent;
import ai.elimu.model.v2.enums.analytics.research.ExperimentGroup;
import ai.elimu.model.v2.enums.analytics.research.ResearchExperiment;

import java.util.Calendar;
import java.util.List;

import jakarta.persistence.NoResultException;
import org.springframework.dao.DataAccessException;

public class VideoLearningEventDaoJpa extends GenericDaoJpa<VideoLearningEvent> implements VideoLearningEventDao {

    @Override
    public VideoLearningEvent read(Calendar timestamp, String androidId, String packageName) throws DataAccessException {
        try {
            return (VideoLearningEvent) em.createQuery(
                "SELECT event " +
                "FROM VideoLearningEvent event " +
                "WHERE event.timestamp = :timestamp " +
                "AND event.androidId = :androidId " + 
                "AND event.packageName = :packageName")
                .setParameter("timestamp", timestamp)
                .setParameter("androidId", androidId)
                .setParameter("packageName", packageName)
                .getSingleResult();
        } catch (NoResultException e) {
            logger.info("VideoLearningEvent (" + timestamp.getTime() + ", " + androidId + ", " + packageName + ") was not found");
            return null;
        }
    }

    @Override
    public List<VideoLearningEvent> readAllOrderedByTimestamp(OrderDirection orderDirection) throws DataAccessException {
        return em.createQuery(
            "SELECT event " + 
            "FROM VideoLearningEvent event " +
            "ORDER BY event.timestamp " + orderDirection)
            .getResultList();
    }

    @Override
    public List<VideoLearningEvent> readAll(String androidId) throws DataAccessException {
        return em.createQuery(
            "SELECT event " + 
            "FROM VideoLearningEvent event " +
            "WHERE event.androidId = :androidId " + 
            "ORDER BY event.timestamp")
            .setParameter("androidId", androidId)
            .getResultList();
    }

    @Override
    public List<VideoLearningEvent> readAll(ResearchExperiment researchExperiment, ExperimentGroup experimentGroup)
            throws DataAccessException {
        return em.createQuery(
            "SELECT event " + 
            "FROM VideoLearningEvent event " +
            "WHERE event.researchExperiment = :researchExperiment " + 
            "AND event.experimentGroup = :experimentGroup " +
            "ORDER BY event.timestamp")
            .setParameter("researchExperiment", researchExperiment)
            .setParameter("experimentGroup", experimentGroup)
            .getResultList();
    }
}
