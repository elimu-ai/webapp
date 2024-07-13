package ai.elimu.dao.jpa;

import ai.elimu.dao.LetterSoundPeerReviewEventDao;
import ai.elimu.model.content.LetterSound;
import ai.elimu.model.contributor.Contributor;
import ai.elimu.model.contributor.LetterSoundContributionEvent;
import ai.elimu.model.contributor.LetterSoundPeerReviewEvent;
import java.util.List;
import org.springframework.dao.DataAccessException;

public class LetterSoundPeerReviewEventDaoJpa extends GenericDaoJpa<LetterSoundPeerReviewEvent> implements LetterSoundPeerReviewEventDao {
    
    @Override
    public List<LetterSoundPeerReviewEvent> readAll(LetterSoundContributionEvent letterSoundContributionEvent, Contributor contributor) throws DataAccessException {
        return em.createQuery(
            "SELECT event " +
            "FROM LetterSoundPeerReviewEvent event " +
            "WHERE event.letterSoundContributionEvent = :letterSoundContributionEvent " +
            "AND event.contributor = :contributor " +
            "ORDER BY event.time DESC")
            .setParameter("letterSoundContributionEvent", letterSoundContributionEvent)
            .setParameter("contributor", contributor)
            .getResultList();
    }

    @Override
    public List<LetterSoundPeerReviewEvent> readAll(LetterSound letterSound) throws DataAccessException {
        return em.createQuery(
            "SELECT event " + 
            "FROM LetterSoundPeerReviewEvent event " +
            "WHERE event.letterSoundContributionEvent.letterSound = :letterSound " + 
            "ORDER BY event.time DESC")
            .setParameter("letterSound", letterSound)
            .getResultList();
    }
    
    @Override
    public List<LetterSoundPeerReviewEvent> readAll(Contributor contributor) throws DataAccessException {
        return em.createQuery(
            "SELECT event " + 
            "FROM LetterSoundPeerReviewEvent event " +
            "WHERE event.contributor = :contributor " + 
            "ORDER BY event.time DESC")
            .setParameter("contributor", contributor)
            .getResultList();
    }
    
    @Override
    public List<LetterSoundPeerReviewEvent> readAll(LetterSoundContributionEvent letterSoundContributionEvent) throws DataAccessException {
        return em.createQuery(
            "SELECT event " + 
            "FROM LetterSoundPeerReviewEvent event " +
            "WHERE event.letterSoundContributionEvent = :letterSoundContributionEvent " + 
            "ORDER BY event.time DESC")
            .setParameter("letterSoundContributionEvent", letterSoundContributionEvent)
            .getResultList();
    }
    
    @Override
    public Long readCount(Contributor contributor) throws DataAccessException {
        return (Long) em.createQuery("SELECT COUNT(event) " +
                "FROM LetterSoundPeerReviewEvent event " +
                "WHERE event.contributor = :contributor")
                .setParameter("contributor", contributor)
                .getSingleResult();
    }
}
