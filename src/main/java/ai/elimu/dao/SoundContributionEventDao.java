package ai.elimu.dao;

import java.util.List;
import org.springframework.dao.DataAccessException;

import ai.elimu.entity.content.Sound;
import ai.elimu.entity.contributor.Contributor;
import ai.elimu.entity.contributor.SoundContributionEvent;

public interface SoundContributionEventDao extends GenericDao<SoundContributionEvent> {
    
    List<SoundContributionEvent> readAllOrderedByTimeDesc() throws DataAccessException;
    
    List<SoundContributionEvent> readAll(Sound sound) throws DataAccessException;
    
    List<SoundContributionEvent> readAll(Contributor contributor) throws DataAccessException;
    
    List<SoundContributionEvent> readMostRecent(int maxResults) throws DataAccessException;
    
    List<SoundContributionEvent> readMostRecentPerSound() throws DataAccessException;
    
    Long readCount(Contributor contributor) throws DataAccessException;
}
