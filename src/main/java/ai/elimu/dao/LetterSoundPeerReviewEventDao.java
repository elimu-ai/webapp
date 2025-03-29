package ai.elimu.dao;

import java.util.List;
import org.springframework.dao.DataAccessException;

import ai.elimu.entity.content.LetterSound;
import ai.elimu.entity.contributor.Contributor;
import ai.elimu.entity.contributor.LetterSoundContributionEvent;
import ai.elimu.entity.contributor.LetterSoundPeerReviewEvent;

public interface LetterSoundPeerReviewEventDao extends GenericDao<LetterSoundPeerReviewEvent> {
    
    List<LetterSoundPeerReviewEvent> readAll(LetterSoundContributionEvent letterSoundContributionEvent, Contributor contributor) throws DataAccessException;
    
    List<LetterSoundPeerReviewEvent> readAll(LetterSound letterSound) throws DataAccessException;
    
    List<LetterSoundPeerReviewEvent> readAll(Contributor contributor) throws DataAccessException;
    
    List<LetterSoundPeerReviewEvent> readAll(LetterSoundContributionEvent letterSoundContributionEvent) throws DataAccessException;
    
    Long readCount(Contributor contributor) throws DataAccessException;
}
