package ai.elimu.dao;

import java.util.List;
import org.springframework.dao.DataAccessException;

import ai.elimu.entity.content.LetterSound;
import ai.elimu.entity.contributor.Contributor;
import ai.elimu.entity.contributor.LetterSoundContributionEvent;

public interface LetterSoundContributionEventDao extends GenericDao<LetterSoundContributionEvent> {

    List<LetterSoundContributionEvent> readAllOrderedByTimeDesc() throws DataAccessException;

    List<LetterSoundContributionEvent> readAll(LetterSound letterSound) throws DataAccessException;

    List<LetterSoundContributionEvent> readAll(Contributor contributor) throws DataAccessException;

    List<LetterSoundContributionEvent> readMostRecentPerLetterSound() throws DataAccessException;
    
    Long readCount(Contributor contributor) throws DataAccessException;
}
