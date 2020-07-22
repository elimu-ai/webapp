package ai.elimu.dao.jpa;

import ai.elimu.model.contributor.StoryBookContributionEvent;
import ai.elimu.dao.StoryBookContributionEventDao;
import ai.elimu.model.content.StoryBook;
import ai.elimu.model.contributor.Contributor;
import java.util.List;
import org.springframework.dao.DataAccessException;

public class StoryBookContributionEventDaoJpa extends GenericDaoJpa<StoryBookContributionEvent> implements StoryBookContributionEventDao {

    @Override
    public List<StoryBookContributionEvent> readAll(StoryBook storyBook) throws DataAccessException {
        return em.createQuery(
            "SELECT sce " + 
            "FROM StoryBookContributionEvent sce " +
            "WHERE sce.storyBook = :storyBook " + 
            "ORDER BY sce.time DESC")
            .setParameter("storyBook", storyBook)
            .getResultList();
    }
    
    @Override
    public List<StoryBookContributionEvent> readMostRecent(int maxResults) throws DataAccessException {
        return em.createQuery(
            "SELECT sce " + 
            "FROM StoryBookContributionEvent sce " +
            "ORDER BY sce.time DESC")
            .setMaxResults(maxResults)
            .getResultList();
    }
    
    @Override
    public Long readCount(Contributor contributor) throws DataAccessException {
        return (Long) em.createQuery("SELECT COUNT(sce) " +
                "FROM StoryBookContributionEvent sce " +
                "WHERE sce.contributor = :contributor")
                .setParameter("contributor", contributor)
                .getSingleResult();
    }
}
