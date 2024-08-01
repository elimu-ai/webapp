package ai.elimu.dao.jpa;

import ai.elimu.dao.AudioPeerReviewEventDao;
import ai.elimu.model.content.multimedia.Audio;
import ai.elimu.model.contributor.AudioContributionEvent;
import ai.elimu.model.contributor.AudioPeerReviewEvent;
import ai.elimu.model.contributor.Contributor;
import java.util.List;
import jakarta.persistence.NoResultException;
import org.springframework.dao.DataAccessException;

public class AudioPeerReviewEventDaoJpa extends GenericDaoJpa<AudioPeerReviewEvent> implements AudioPeerReviewEventDao {

    @Override
    public AudioPeerReviewEvent read(AudioContributionEvent audioContributionEvent, Contributor contributor) throws DataAccessException {
        try {
            return (AudioPeerReviewEvent) em.createQuery(
                "SELECT apre " +
                "FROM AudioPeerReviewEvent apre " +
                "WHERE apre.audioContributionEvent = :audioContributionEvent " +
                "AND apre.contributor = :contributor")
                .setParameter("audioContributionEvent", audioContributionEvent)
                .setParameter("contributor", contributor)
                .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
    
    @Override
    public List<AudioPeerReviewEvent> readAll(Audio audio) throws DataAccessException {
        return em.createQuery(
            "SELECT apre " + 
            "FROM AudioPeerReviewEvent apre " +
            "WHERE apre.audioContributionEvent.audio = :audio " + 
            "ORDER BY apre.timestamp DESC")
            .setParameter("audio", audio)
            .getResultList();
    }
    
    @Override
    public List<AudioPeerReviewEvent> readAll(AudioContributionEvent audioContributionEvent) throws DataAccessException {
        return em.createQuery(
            "SELECT apre " + 
            "FROM AudioPeerReviewEvent apre " +
            "WHERE apre.audioContributionEvent = :audioContributionEvent " + 
            "ORDER BY apre.timestamp DESC")
            .setParameter("audioContributionEvent", audioContributionEvent)
            .getResultList();
    }
    
    @Override
    public Long readCount(Contributor contributor) throws DataAccessException {
        return (Long) em.createQuery("SELECT COUNT(apre) " +
                "FROM AudioPeerReviewEvent apre " +
                "WHERE apre.contributor = :contributor")
                .setParameter("contributor", contributor)
                .getSingleResult();
    }
}
