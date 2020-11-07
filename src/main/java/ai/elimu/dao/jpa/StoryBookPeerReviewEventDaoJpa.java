package ai.elimu.dao.jpa;

import ai.elimu.dao.StoryBookPeerReviewEventDao;
import ai.elimu.model.content.StoryBook;
import ai.elimu.model.contributor.StoryBookContributionEvent;
import ai.elimu.model.contributor.StoryBookPeerReviewEvent;
import java.util.List;
import org.springframework.dao.DataAccessException;

public class StoryBookPeerReviewEventDaoJpa extends GenericDaoJpa<StoryBookPeerReviewEvent> implements StoryBookPeerReviewEventDao {

    @Override
    public List<StoryBookPeerReviewEvent> readAll(StoryBook storyBook) throws DataAccessException {
        return em.createQuery(
            "SELECT sbpre " + 
            "FROM StoryBookPeerReviewEvent sbpre " +
            "WHERE sbpre.storyBookContributionEvent.storyBook = :storyBook " + 
            "ORDER BY sbpre.time DESC")
            .setParameter("storyBook", storyBook)
            .getResultList();
    }
    
    @Override
    public List<StoryBookPeerReviewEvent> readAll(StoryBookContributionEvent storyBookContributionEvent) throws DataAccessException {
        return em.createQuery(
            "SELECT sbpre " + 
            "FROM StoryBookPeerReviewEvent sbpre " +
            "WHERE sbpre.storyBookContributionEvent = :storyBookContributionEvent " + 
            "ORDER BY sbpre.time DESC")
            .setParameter("storyBookContributionEvent", storyBookContributionEvent)
            .getResultList();
    }
}
