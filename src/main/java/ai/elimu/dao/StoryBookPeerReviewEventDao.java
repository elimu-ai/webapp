package ai.elimu.dao;

import ai.elimu.model.content.StoryBook;
import ai.elimu.model.contributor.Contributor;
import ai.elimu.model.contributor.StoryBookContributionEvent;
import ai.elimu.model.contributor.StoryBookPeerReviewEvent;
import java.util.List;
import org.springframework.dao.DataAccessException;

public interface StoryBookPeerReviewEventDao extends GenericDao<StoryBookPeerReviewEvent> {
    
    List<StoryBookPeerReviewEvent> readAll(StoryBookContributionEvent storyBookContributionEvent, Contributor contributor) throws DataAccessException;
    
    List<StoryBookPeerReviewEvent> readAll(StoryBook storyBook) throws DataAccessException;
    
    List<StoryBookPeerReviewEvent> readAll(Contributor contributor) throws DataAccessException;
    
    List<StoryBookPeerReviewEvent> readAll(StoryBookContributionEvent storyBookContributionEvent) throws DataAccessException;
    
    Long readCount(Contributor contributor) throws DataAccessException;
}
