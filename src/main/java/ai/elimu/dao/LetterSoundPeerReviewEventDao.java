package ai.elimu.dao;

import ai.elimu.model.content.LetterSoundCorrespondence;
import ai.elimu.model.contributor.Contributor;
import ai.elimu.model.contributor.LetterSoundCorrespondenceContributionEvent;
import ai.elimu.model.contributor.LetterSoundPeerReviewEvent;
import java.util.List;
import org.springframework.dao.DataAccessException;

public interface LetterSoundPeerReviewEventDao extends GenericDao<LetterSoundPeerReviewEvent> {
    
    List<LetterSoundPeerReviewEvent> readAll(LetterSoundCorrespondenceContributionEvent letterSoundContributionEvent, Contributor contributor) throws DataAccessException;
    
    List<LetterSoundPeerReviewEvent> readAll(LetterSoundCorrespondence letterSoundCorrespondence) throws DataAccessException;
    
    List<LetterSoundPeerReviewEvent> readAll(Contributor contributor) throws DataAccessException;
    
    List<LetterSoundPeerReviewEvent> readAll(LetterSoundCorrespondenceContributionEvent letterSoundContributionEvent) throws DataAccessException;
    
    Long readCount(Contributor contributor) throws DataAccessException;
}
