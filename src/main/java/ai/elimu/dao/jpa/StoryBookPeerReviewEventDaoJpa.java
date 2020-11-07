package ai.elimu.dao.jpa;

import ai.elimu.dao.StoryBookPeerReviewEventDao;
import ai.elimu.model.content.StoryBook;
import ai.elimu.model.contributor.StoryBookPeerReviewEvent;
import java.util.List;
import org.springframework.dao.DataAccessException;

public class StoryBookPeerReviewEventDaoJpa extends GenericDaoJpa<StoryBookPeerReviewEvent> implements StoryBookPeerReviewEventDao {

    @Override
    public List<StoryBookPeerReviewEvent> readAll(StoryBook storyBook) throws DataAccessException {
        return em.createQuery(
            "SELECT spre " + 
            "FROM StoryBookPeerReviewEvent spre " +
            "WHERE spre.storyBook = :storyBook " + 
            "ORDER BY spre.time DESC")
            .setParameter("storyBook", storyBook)
            .getResultList();
    }
}
