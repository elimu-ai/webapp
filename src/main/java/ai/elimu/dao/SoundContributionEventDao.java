package ai.elimu.dao;

import ai.elimu.model.content.Sound;
import ai.elimu.model.contributor.Contributor;
import ai.elimu.model.contributor.SoundContributionEvent;
import java.util.List;
import org.springframework.dao.DataAccessException;

public interface SoundContributionEventDao extends GenericDao<SoundContributionEvent> {
    
    List<SoundContributionEvent> readAllOrderedByTimeDesc() throws DataAccessException;
    
    List<SoundContributionEvent> readAll(Sound sound) throws DataAccessException;
    
    List<SoundContributionEvent> readAll(Contributor contributor) throws DataAccessException;
    
    List<SoundContributionEvent> readMostRecent(int maxResults) throws DataAccessException;
    
    List<SoundContributionEvent> readMostRecentPerSound() throws DataAccessException;
    
    Long readCount(Contributor contributor) throws DataAccessException;
}
