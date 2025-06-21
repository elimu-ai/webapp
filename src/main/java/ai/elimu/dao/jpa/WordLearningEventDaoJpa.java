package ai.elimu.dao.jpa;

import ai.elimu.dao.WordLearningEventDao;
import ai.elimu.entity.analytics.WordLearningEvent;
import ai.elimu.model.v2.enums.analytics.research.ExperimentGroup;
import ai.elimu.model.v2.enums.analytics.research.ResearchExperiment;

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

    @Override
    public List<WordLearningEvent> readAll(ResearchExperiment researchExperiment, ExperimentGroup experimentGroup)
            throws DataAccessException {
        return em.createQuery(
            "SELECT event " + 
            "FROM WordLearningEvent event " +
            "WHERE event.researchExperiment = :researchExperiment " + 
            "AND event.experimentGroup = :experimentGroup " +
            "ORDER BY event.timestamp")
            .setParameter("researchExperiment", researchExperiment)
            .setParameter("experimentGroup", experimentGroup)
            .getResultList();
    }
}
