package ai.elimu.dao.jpa;

import java.util.Calendar;
import java.util.List;

import org.springframework.dao.DataAccessException;

import ai.elimu.dao.LetterSoundLearningEventDao;
import ai.elimu.entity.analytics.LetterSoundLearningEvent;
import ai.elimu.model.v2.enums.analytics.research.ExperimentGroup;
import ai.elimu.model.v2.enums.analytics.research.ResearchExperiment;
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
            logger.info("LetterSoundLearningEvent (" + timestamp.getTime() + ", " + androidId + ", " + packageName + ") was not found");
            return null;
        }
    }
    
    @Override
    public List<LetterSoundLearningEvent> readAll(String androidId) throws DataAccessException {
        return em.createQuery(
            "SELECT event " + 
            "FROM LetterSoundLearningEvent event " +
            "WHERE event.androidId = :androidId " + 
            "ORDER BY event.timestamp")
            .setParameter("androidId", androidId)
            .getResultList();
    }

    @Override
    public List<LetterSoundLearningEvent> readAll(ResearchExperiment researchExperiment,
            ExperimentGroup experimentGroup) throws DataAccessException {
        return em.createQuery(
            "SELECT event " + 
            "FROM LetterSoundLearningEvent event " +
            "WHERE event.researchExperiment = :researchExperiment " + 
            "AND event.experimentGroup = :experimentGroup " +
            "ORDER BY event.timestamp")
            .setParameter("researchExperiment", researchExperiment)
            .setParameter("experimentGroup", experimentGroup)
            .getResultList();
    }
}
