package ai.elimu.dao.jpa;

import ai.elimu.dao.AudioPeerReviewEventDao;
import ai.elimu.model.content.multimedia.Audio;
import ai.elimu.model.contributor.AudioContributionEvent;
import ai.elimu.model.contributor.AudioPeerReviewEvent;
import java.util.List;
import org.springframework.dao.DataAccessException;

public class AudioPeerReviewEventDaoJpa extends GenericDaoJpa<AudioPeerReviewEvent> implements AudioPeerReviewEventDao {

    @Override
    public List<AudioPeerReviewEvent> readAll(Audio audio) throws DataAccessException {
        return em.createQuery(
            "SELECT apre " + 
            "FROM AudioPeerReviewEvent apre " +
            "WHERE apre.audioContributionEvent.audio = :audio " + 
            "ORDER BY apre.time DESC")
            .setParameter("audio", audio)
            .getResultList();
    }
    
    @Override
    public List<AudioPeerReviewEvent> readAll(AudioContributionEvent audioContributionEvent) throws DataAccessException {
        return em.createQuery(
            "SELECT apre " + 
            "FROM AudioPeerReviewEvent apre " +
            "WHERE apre.audioContributionEvent = :audioContributionEvent " + 
            "ORDER BY apre.time DESC")
            .setParameter("audioContributionEvent", audioContributionEvent)
            .getResultList();
    }
}
