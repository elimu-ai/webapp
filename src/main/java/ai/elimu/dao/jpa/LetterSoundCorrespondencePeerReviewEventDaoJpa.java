package ai.elimu.dao.jpa;

import ai.elimu.dao.LetterSoundCorrespondencePeerReviewEventDao;
import ai.elimu.model.content.LetterSoundCorrespondence;
import ai.elimu.model.contributor.Contributor;
import ai.elimu.model.contributor.LetterSoundCorrespondenceContributionEvent;
import ai.elimu.model.contributor.LetterSoundCorrespondencePeerReviewEvent;
import java.util.List;
import org.springframework.dao.DataAccessException;

public class LetterSoundCorrespondencePeerReviewEventDaoJpa extends GenericDaoJpa<LetterSoundCorrespondencePeerReviewEvent> implements LetterSoundCorrespondencePeerReviewEventDao {
    
    @Override
    public List<LetterSoundCorrespondencePeerReviewEvent> readAll(LetterSoundCorrespondenceContributionEvent letterSoundCorrespondenceContributionEvent, Contributor contributor) throws DataAccessException {
        return em.createQuery(
            "SELECT event " +
            "FROM LetterSoundCorrespondencePeerReviewEvent event " +
            "WHERE event.letterSoundCorrespondenceContributionEvent = :letterSoundCorrespondenceContributionEvent " +
            "AND event.contributor = :contributor " +
            "ORDER BY event.time DESC")
            .setParameter("letterSoundCorrespondenceContributionEvent", letterSoundCorrespondenceContributionEvent)
            .setParameter("contributor", contributor)
            .getResultList();
    }

    @Override
    public List<LetterSoundCorrespondencePeerReviewEvent> readAll(LetterSoundCorrespondence letterSoundCorrespondence) throws DataAccessException {
        return em.createQuery(
            "SELECT event " + 
            "FROM LetterSoundCorrespondencePeerReviewEvent event " +
            "WHERE event.letterSoundCorrespondenceContributionEvent.letterSoundCorrespondence = :letterSoundCorrespondence " + 
            "ORDER BY event.time DESC")
            .setParameter("letterSoundCorrespondence", letterSoundCorrespondence)
            .getResultList();
    }
    
    @Override
    public List<LetterSoundCorrespondencePeerReviewEvent> readAll(Contributor contributor) throws DataAccessException {
        return em.createQuery(
            "SELECT event " + 
            "FROM LetterSoundCorrespondencePeerReviewEvent event " +
            "WHERE event.contributor = :contributor " + 
            "ORDER BY event.time DESC")
            .setParameter("contributor", contributor)
            .getResultList();
    }
    
    @Override
    public List<LetterSoundCorrespondencePeerReviewEvent> readAll(LetterSoundCorrespondenceContributionEvent letterSoundCorrespondenceContributionEvent) throws DataAccessException {
        return em.createQuery(
            "SELECT event " + 
            "FROM LetterSoundCorrespondencePeerReviewEvent event " +
            "WHERE event.letterSoundCorrespondenceContributionEvent = :letterSoundCorrespondenceContributionEvent " + 
            "ORDER BY event.time DESC")
            .setParameter("letterSoundCorrespondenceContributionEvent", letterSoundCorrespondenceContributionEvent)
            .getResultList();
    }
    
    @Override
    public Long readCount(Contributor contributor) throws DataAccessException {
        return (Long) em.createQuery("SELECT COUNT(event) " +
                "FROM LetterSoundCorrespondencePeerReviewEvent event " +
                "WHERE event.contributor = :contributor")
                .setParameter("contributor", contributor)
                .getSingleResult();
    }
}
