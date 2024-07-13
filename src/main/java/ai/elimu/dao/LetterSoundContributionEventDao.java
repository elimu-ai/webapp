package ai.elimu.dao;

import ai.elimu.model.content.LetterSoundCorrespondence;
import ai.elimu.model.contributor.Contributor;
import ai.elimu.model.contributor.LetterSoundContributionEvent;
import java.util.List;
import org.springframework.dao.DataAccessException;

public interface LetterSoundContributionEventDao extends GenericDao<LetterSoundContributionEvent> {

    List<LetterSoundContributionEvent> readAllOrderedByTimeDesc() throws DataAccessException;

    List<LetterSoundContributionEvent> readAll(LetterSoundCorrespondence letterSound) throws DataAccessException;

    List<LetterSoundContributionEvent> readAll(Contributor contributor) throws DataAccessException;

    List<LetterSoundContributionEvent> readMostRecentPerLetterSound() throws DataAccessException;
    
    Long readCount(Contributor contributor) throws DataAccessException;
}
