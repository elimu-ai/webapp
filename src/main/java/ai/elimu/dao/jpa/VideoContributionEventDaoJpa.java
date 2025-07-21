package ai.elimu.dao.jpa;

import ai.elimu.dao.VideoContributionEventDao;
import ai.elimu.entity.content.multimedia.Video;
import ai.elimu.entity.contributor.Contributor;
import ai.elimu.entity.contributor.VideoContributionEvent;

import java.util.List;
import org.springframework.dao.DataAccessException;

public class VideoContributionEventDaoJpa extends GenericDaoJpa<VideoContributionEvent> implements VideoContributionEventDao {

    @Override
    public List<VideoContributionEvent> readAll(Video video) throws DataAccessException {
        return em.createQuery(
            "SELECT event " + 
            "FROM VideoContributionEvent event " +
            "WHERE event.video = :video " + 
            "ORDER BY event.timestamp DESC")
            .setParameter("video", video)
            .getResultList();
    }
    
    @Override
    public List<VideoContributionEvent> readAll(Contributor contributor) throws DataAccessException {
        return em.createQuery(
            "SELECT event " + 
            "FROM VideoContributionEvent event " +
            "WHERE event.contributor = :contributor " + 
            "ORDER BY event.timestamp DESC")
            .setParameter("contributor", contributor)
            .getResultList();
    }

    @Override
    public Long readCount(Contributor contributor) throws DataAccessException {
        return (Long) em.createQuery(
            "SELECT COUNT(event) " +
            "FROM VideoContributionEvent event " +
            "WHERE event.contributor = :contributor")
            .setParameter("contributor", contributor)
            .getSingleResult();
    }
}
