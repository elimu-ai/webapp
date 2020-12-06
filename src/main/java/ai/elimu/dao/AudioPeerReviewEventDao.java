package ai.elimu.dao;

import ai.elimu.model.content.multimedia.Audio;
import ai.elimu.model.contributor.AudioContributionEvent;
import ai.elimu.model.contributor.AudioPeerReviewEvent;
import java.util.List;
import org.springframework.dao.DataAccessException;

public interface AudioPeerReviewEventDao extends GenericDao<AudioPeerReviewEvent> {
    
    List<AudioPeerReviewEvent> readAll(Audio audio) throws DataAccessException;
    
    List<AudioPeerReviewEvent> readAll(AudioContributionEvent audioContributionEvent) throws DataAccessException;
}
