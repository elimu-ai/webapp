package ai.elimu.dao.jpa;

import ai.elimu.model.contributor.ImageContributionEvent;
import ai.elimu.dao.ImageContributionEventDao;
import ai.elimu.model.content.multimedia.Image;
import ai.elimu.model.contributor.Contributor;
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
    public Long readCount(Contributor contributor) throws DataAccessException {
        return (Long) em.createQuery("SELECT COUNT(ice) " +
                "FROM ImageContributionEvent ice " +
                "WHERE ice.contributor = :contributor")
                .setParameter("contributor", contributor)
                .getSingleResult();
    }

    @Override
    public void deleteAllEventsForImage(Image image) {
        em.createQuery("DELETE " +
                        "FROM ImageContributionEvent ice " +
                        "WHERE ice.image = :image")
                .setParameter("image", image)
                .executeUpdate();
    }

}
