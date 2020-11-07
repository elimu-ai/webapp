package ai.elimu.dao;

import ai.elimu.model.content.StoryBook;
import ai.elimu.model.contributor.StoryBookPeerReviewEvent;
import java.util.List;
import org.springframework.dao.DataAccessException;

public interface StoryBookPeerReviewEventDao extends GenericDao<StoryBookPeerReviewEvent> {
    
    List<StoryBookPeerReviewEvent> readAll(StoryBook storyBook) throws DataAccessException;
}
