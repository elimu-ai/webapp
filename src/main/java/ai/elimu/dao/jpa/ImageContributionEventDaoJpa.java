package ai.elimu.dao.jpa;

import ai.elimu.entity.contributor.ImageContributionEvent;
import ai.elimu.dao.ImageContributionEventDao;
import ai.elimu.entity.content.multimedia.Image;
import ai.elimu.entity.contributor.Contributor;
import java.util.List;
import org.springframework.dao.DataAccessException;

public class ImageContributionEventDaoJpa extends GenericDaoJpa<ImageContributionEvent> implements ImageContributionEventDao {

    @Override
    public List<ImageContributionEvent> readAll(Image image) throws DataAccessException {
        return em.createQuery(
            "SELECT ice " + 
            "FROM ImageContributionEvent ice " +
            "WHERE ice.image = :image " + 
            "ORDER BY ice.time DESC")
            .setParameter("image", image)
            .getResultList();
    }
    
    @Override
    public List<ImageContributionEvent> readAll(Contributor contributor) throws DataAccessException {
        return em.createQuery(
            "SELECT ice " + 
            "FROM ImageContributionEvent ice " +
            "WHERE ice.contributor = :contributor " + 
            "ORDER BY ice.time DESC")
            .setParameter("contributor", contributor)
            .getResultList();
    }

    @Override
    public List<ImageContributionEvent> readMostRecent(int maxResults) throws DataAccessException {
        return em.createQuery(
            "SELECT ice " + 
            "FROM ImageContributionEvent ice " +
            "ORDER BY ice.time DESC")
            .setMaxResults(maxResults)
            .getResultList();
    }

    @Override
    public Long readCount(Contributor contributor) throws DataAccessException {
        return (Long) em.createQuery("SELECT COUNT(ice) " +
                "FROM ImageContributionEvent ice " +
                "WHERE ice.contributor = :contributor")
                .setParameter("contributor", contributor)
                .getSingleResult();
    }
}
