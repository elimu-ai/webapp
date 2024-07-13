package ai.elimu.dao;

import ai.elimu.model.content.LetterSoundCorrespondence;
import ai.elimu.model.contributor.Contributor;
import ai.elimu.model.contributor.LetterSoundContributionEvent;
import ai.elimu.model.contributor.LetterSoundPeerReviewEvent;
import java.util.List;
import org.springframework.dao.DataAccessException;

public interface LetterSoundPeerReviewEventDao extends GenericDao<LetterSoundPeerReviewEvent> {
    
    List<LetterSoundPeerReviewEvent> readAll(LetterSoundContributionEvent letterSoundContributionEvent, Contributor contributor) throws DataAccessException;
    
    List<LetterSoundPeerReviewEvent> readAll(LetterSoundCorrespondence letterSoundCorrespondence) throws DataAccessException;
    
    List<LetterSoundPeerReviewEvent> readAll(Contributor contributor) throws DataAccessException;
    
    List<LetterSoundPeerReviewEvent> readAll(LetterSoundContributionEvent letterSoundContributionEvent) throws DataAccessException;
    
    Long readCount(Contributor contributor) throws DataAccessException;
}
