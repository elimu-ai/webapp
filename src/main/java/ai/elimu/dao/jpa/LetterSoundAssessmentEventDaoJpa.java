package ai.elimu.dao.jpa;

import java.util.List;

import org.springframework.dao.DataAccessException;

import ai.elimu.dao.LetterSoundAssessmentEventDao;
import ai.elimu.entity.analytics.LetterSoundAssessmentEvent;

public class LetterSoundAssessmentEventDaoJpa extends GenericDaoJpa<LetterSoundAssessmentEvent> implements LetterSoundAssessmentEventDao {

    @Override
    public List<LetterSoundAssessmentEvent> readAll(String androidId) throws DataAccessException {
        return em.createQuery(
            "SELECT event " + 
            "FROM LetterSoundAssessmentEvent event " +
            "WHERE event.androidId = :androidId " + 
            "ORDER BY event.id")
            .setParameter("androidId", androidId)
            .getResultList();
    }
}
