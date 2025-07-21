package ai.elimu.dao;

import java.util.List;
import org.springframework.dao.DataAccessException;

import ai.elimu.entity.content.StoryBook;
import ai.elimu.entity.contributor.Contributor;
import ai.elimu.entity.contributor.StoryBookContributionEvent;

public interface StoryBookContributionEventDao extends GenericDao<StoryBookContributionEvent> {
    
    List<StoryBookContributionEvent> readAllOrderedByTimeDesc() throws DataAccessException;
    
    List<StoryBookContributionEvent> readAll(StoryBook storyBook) throws DataAccessException;
    
    List<StoryBookContributionEvent> readAll(Contributor contributor) throws DataAccessException;
    
    Long readCount(Contributor contributor) throws DataAccessException;
}
