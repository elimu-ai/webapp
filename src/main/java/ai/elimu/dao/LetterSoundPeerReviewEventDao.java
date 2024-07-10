package ai.elimu.dao;

import ai.elimu.entity.content.LetterSoundCorrespondence;
import ai.elimu.entity.contributor.Contributor;
import ai.elimu.entity.contributor.LetterSoundCorrespondenceContributionEvent;
import ai.elimu.entity.contributor.LetterSoundCorrespondencePeerReviewEvent;
import java.util.List;
import org.springframework.dao.DataAccessException;

public interface LetterSoundPeerReviewEventDao extends GenericDao<LetterSoundCorrespondencePeerReviewEvent> {
    
    List<LetterSoundCorrespondencePeerReviewEvent> readAll(LetterSoundCorrespondenceContributionEvent letterSoundCorrespondenceContributionEvent, Contributor contributor) throws DataAccessException;
    
    List<LetterSoundCorrespondencePeerReviewEvent> readAll(LetterSoundCorrespondence letterSoundCorrespondence) throws DataAccessException;
    
    List<LetterSoundCorrespondencePeerReviewEvent> readAll(Contributor contributor) throws DataAccessException;
    
    List<LetterSoundCorrespondencePeerReviewEvent> readAll(LetterSoundCorrespondenceContributionEvent letterSoundCorrespondenceContributionEvent) throws DataAccessException;
    
    Long readCount(Contributor contributor) throws DataAccessException;
}
