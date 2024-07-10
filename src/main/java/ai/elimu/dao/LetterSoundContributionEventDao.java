package ai.elimu.dao;

import ai.elimu.entity.content.LetterSoundCorrespondence;
import ai.elimu.entity.contributor.Contributor;
import ai.elimu.entity.contributor.LetterSoundCorrespondenceContributionEvent;
import java.util.List;
import org.springframework.dao.DataAccessException;

public interface LetterSoundContributionEventDao extends GenericDao<LetterSoundCorrespondenceContributionEvent> {

    List<LetterSoundCorrespondenceContributionEvent> readAllOrderedByTimeDesc() throws DataAccessException;

    List<LetterSoundCorrespondenceContributionEvent> readAll(LetterSoundCorrespondence letterSound) throws DataAccessException;

    List<LetterSoundCorrespondenceContributionEvent> readAll(Contributor contributor) throws DataAccessException;

    List<LetterSoundCorrespondenceContributionEvent> readMostRecentPerLetterSound() throws DataAccessException;
    
    Long readCount(Contributor contributor) throws DataAccessException;
}
