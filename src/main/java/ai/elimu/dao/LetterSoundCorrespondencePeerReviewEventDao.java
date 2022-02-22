package ai.elimu.dao;

import ai.elimu.model.content.LetterSoundCorrespondence;
import ai.elimu.model.contributor.Contributor;
import ai.elimu.model.contributor.LetterSoundCorrespondenceContributionEvent;
import ai.elimu.model.contributor.LetterSoundCorrespondencePeerReviewEvent;
import java.util.List;
import org.springframework.dao.DataAccessException;

public interface LetterSoundCorrespondencePeerReviewEventDao extends GenericDao<LetterSoundCorrespondencePeerReviewEvent> {
    
    List<LetterSoundCorrespondencePeerReviewEvent> readAll(LetterSoundCorrespondenceContributionEvent letterSoundCorrespondenceContributionEvent, Contributor contributor) throws DataAccessException;
    
    List<LetterSoundCorrespondencePeerReviewEvent> readAll(LetterSoundCorrespondence letterSoundCorrespondence) throws DataAccessException;
    
    List<LetterSoundCorrespondencePeerReviewEvent> readAll(Contributor contributor) throws DataAccessException;
    
    List<LetterSoundCorrespondencePeerReviewEvent> readAll(LetterSoundCorrespondenceContributionEvent letterSoundCorrespondenceContributionEvent) throws DataAccessException;
    
    Long readCount(Contributor contributor) throws DataAccessException;
}
