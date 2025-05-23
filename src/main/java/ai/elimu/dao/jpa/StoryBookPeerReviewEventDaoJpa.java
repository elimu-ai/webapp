package ai.elimu.dao.jpa;

import ai.elimu.dao.StoryBookPeerReviewEventDao;
import ai.elimu.entity.content.StoryBook;
import ai.elimu.entity.contributor.Contributor;
import ai.elimu.entity.contributor.StoryBookContributionEvent;
import ai.elimu.entity.contributor.StoryBookPeerReviewEvent;

import java.util.List;
import org.springframework.dao.DataAccessException;

public class StoryBookPeerReviewEventDaoJpa extends GenericDaoJpa<StoryBookPeerReviewEvent> implements StoryBookPeerReviewEventDao {
    
    @Override
    public List<StoryBookPeerReviewEvent> readAll(StoryBookContributionEvent storyBookContributionEvent, Contributor contributor) throws DataAccessException {
        return em.createQuery(
            "SELECT sbpre " +
            "FROM StoryBookPeerReviewEvent sbpre " +
            "WHERE sbpre.storyBookContributionEvent = :storyBookContributionEvent " +
            "AND sbpre.contributor = :contributor " + 
            "ORDER BY sbpre.timestamp DESC")
            .setParameter("storyBookContributionEvent", storyBookContributionEvent)
            .setParameter("contributor", contributor)
            .getResultList();
    }

    @Override
    public List<StoryBookPeerReviewEvent> readAll(StoryBook storyBook) throws DataAccessException {
        return em.createQuery(
            "SELECT sbpre " + 
            "FROM StoryBookPeerReviewEvent sbpre " +
            "WHERE sbpre.storyBookContributionEvent.storyBook = :storyBook " + 
            "ORDER BY sbpre.timestamp DESC")
            .setParameter("storyBook", storyBook)
            .getResultList();
    }
    
    @Override
    public List<StoryBookPeerReviewEvent> readAll(Contributor contributor) throws DataAccessException {
        return em.createQuery(
            "SELECT sbpre " + 
            "FROM StoryBookPeerReviewEvent sbpre " +
            "WHERE sbpre.contributor = :contributor " + 
            "ORDER BY sbpre.timestamp DESC")
            .setParameter("contributor", contributor)
            .getResultList();
    }
    
    @Override
    public List<StoryBookPeerReviewEvent> readAll(StoryBookContributionEvent storyBookContributionEvent) throws DataAccessException {
        return em.createQuery(
            "SELECT sbpre " + 
            "FROM StoryBookPeerReviewEvent sbpre " +
            "WHERE sbpre.storyBookContributionEvent = :storyBookContributionEvent " + 
            "ORDER BY sbpre.timestamp DESC")
            .setParameter("storyBookContributionEvent", storyBookContributionEvent)
            .getResultList();
    }

    @Override
    public Long readCount(Contributor contributor) throws DataAccessException {
        return (Long) em.createQuery("SELECT COUNT(sbpre) " +
                "FROM StoryBookPeerReviewEvent sbpre " +
                "WHERE sbpre.contributor = :contributor")
                .setParameter("contributor", contributor)
                .getSingleResult();
    }
}
