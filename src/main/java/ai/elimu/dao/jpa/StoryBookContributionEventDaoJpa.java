package ai.elimu.dao.jpa;

import ai.elimu.dao.StoryBookContributionEventDao;
import ai.elimu.entity.content.StoryBook;
import ai.elimu.entity.contributor.Contributor;
import ai.elimu.entity.contributor.StoryBookContributionEvent;

import java.util.List;
import org.springframework.dao.DataAccessException;

public class StoryBookContributionEventDaoJpa extends GenericDaoJpa<StoryBookContributionEvent> implements StoryBookContributionEventDao {
    
    @Override
    public List<StoryBookContributionEvent> readAllOrderedByTimeDesc() throws DataAccessException {
        return em.createQuery(
            "SELECT sbce " + 
            "FROM StoryBookContributionEvent sbce " +
            "ORDER BY sbce.timestamp DESC")
            .getResultList();
    }

    @Override
    public List<StoryBookContributionEvent> readAll(StoryBook storyBook) throws DataAccessException {
        return em.createQuery(
            "SELECT sbce " + 
            "FROM StoryBookContributionEvent sbce " +
            "WHERE sbce.storyBook = :storyBook " + 
            "ORDER BY sbce.timestamp DESC")
            .setParameter("storyBook", storyBook)
            .getResultList();
    }
    
    @Override
    public List<StoryBookContributionEvent> readAll(Contributor contributor) throws DataAccessException {
        return em.createQuery(
            "SELECT sbce " + 
            "FROM StoryBookContributionEvent sbce " +
            "WHERE sbce.contributor = :contributor " + 
            "ORDER BY sbce.timestamp DESC")
            .setParameter("contributor", contributor)
            .getResultList();
    }
    
    @Override
    public Long readCount(Contributor contributor) throws DataAccessException {
        return (Long) em.createQuery("SELECT COUNT(sbce) " +
                "FROM StoryBookContributionEvent sbce " +
                "WHERE sbce.contributor = :contributor")
                .setParameter("contributor", contributor)
                .getSingleResult();
    }
}
