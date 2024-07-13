package ai.elimu.dao.jpa;

import ai.elimu.dao.LetterSoundPeerReviewEventDao;
import ai.elimu.model.content.LetterSoundCorrespondence;
import ai.elimu.model.contributor.Contributor;
import ai.elimu.model.contributor.LetterSoundCorrespondenceContributionEvent;
import ai.elimu.model.contributor.LetterSoundCorrespondencePeerReviewEvent;
import java.util.List;
import org.springframework.dao.DataAccessException;

public class LetterSoundPeerReviewEventDaoJpa extends GenericDaoJpa<LetterSoundCorrespondencePeerReviewEvent> implements LetterSoundPeerReviewEventDao {
    
    @Override
    public List<LetterSoundCorrespondencePeerReviewEvent> readAll(LetterSoundCorrespondenceContributionEvent letterSoundContributionEvent, Contributor contributor) throws DataAccessException {
        return em.createQuery(
            "SELECT event " +
            "FROM LetterSoundCorrespondencePeerReviewEvent event " +
            "WHERE event.letterSoundContributionEvent = :letterSoundContributionEvent " +
            "AND event.contributor = :contributor " +
            "ORDER BY event.time DESC")
            .setParameter("letterSoundContributionEvent", letterSoundContributionEvent)
            .setParameter("contributor", contributor)
            .getResultList();
    }

    @Override
    public List<LetterSoundCorrespondencePeerReviewEvent> readAll(LetterSoundCorrespondence letterSound) throws DataAccessException {
        return em.createQuery(
            "SELECT event " + 
            "FROM LetterSoundCorrespondencePeerReviewEvent event " +
            "WHERE event.letterSoundContributionEvent.letterSound = :letterSound " + 
            "ORDER BY event.time DESC")
            .setParameter("letterSound", letterSound)
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
    public List<LetterSoundCorrespondencePeerReviewEvent> readAll(LetterSoundCorrespondenceContributionEvent letterSoundContributionEvent) throws DataAccessException {
        return em.createQuery(
            "SELECT event " + 
            "FROM LetterSoundCorrespondencePeerReviewEvent event " +
            "WHERE event.letterSoundContributionEvent = :letterSoundContributionEvent " + 
            "ORDER BY event.time DESC")
            .setParameter("letterSoundContributionEvent", letterSoundContributionEvent)
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
