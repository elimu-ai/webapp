package ai.elimu.dao;

import ai.elimu.model.content.StoryBook;
import ai.elimu.model.contributor.Contributor;
import ai.elimu.model.contributor.StoryBookContributionEvent;
import java.util.List;
import org.springframework.dao.DataAccessException;

public interface StoryBookContributionEventDao extends GenericDao<StoryBookContributionEvent> {
    
    List<StoryBookContributionEvent> readAllOrderedByTimeDesc() throws DataAccessException;
    
    List<StoryBookContributionEvent> readAll(StoryBook storyBook) throws DataAccessException;
    
    List<StoryBookContributionEvent> readAll(Contributor contributor) throws DataAccessException;
    
    List<StoryBookContributionEvent> readMostRecent(int maxResults) throws DataAccessException;
    
    List<StoryBookContributionEvent> readMostRecentPerStoryBook() throws DataAccessException;
    
    Long readCount(Contributor contributor) throws DataAccessException;
}
