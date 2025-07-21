package ai.elimu.dao.jpa;

import ai.elimu.dao.SoundContributionEventDao;
import ai.elimu.entity.content.Sound;
import ai.elimu.entity.contributor.Contributor;
import ai.elimu.entity.contributor.SoundContributionEvent;

import java.util.List;
import org.springframework.dao.DataAccessException;

public class SoundContributionEventDaoJpa extends GenericDaoJpa<SoundContributionEvent> implements SoundContributionEventDao {
    
    @Override
    public List<SoundContributionEvent> readAllOrderedByTimeDesc() throws DataAccessException {
        return em.createQuery(
            "SELECT event " + 
            "FROM SoundContributionEvent event " +
            "ORDER BY event.timestamp DESC")
            .getResultList();
    }

    @Override
    public List<SoundContributionEvent> readAll(Sound sound) throws DataAccessException {
        return em.createQuery(
            "SELECT event " + 
            "FROM SoundContributionEvent event " +
            "WHERE event.sound = :sound " + 
            "ORDER BY event.timestamp DESC")
            .setParameter("sound", sound)
            .getResultList();
    }
    
    @Override
    public List<SoundContributionEvent> readAll(Contributor contributor) throws DataAccessException {
        return em.createQuery(
            "SELECT event " + 
            "FROM SoundContributionEvent event " +
            "WHERE event.contributor = :contributor " + 
            "ORDER BY event.timestamp DESC")
            .setParameter("contributor", contributor)
            .getResultList();
    }

    @Override
    public Long readCount(Contributor contributor) throws DataAccessException {
        return (Long) em.createQuery("SELECT COUNT(event) " +
                "FROM SoundContributionEvent event " +
                "WHERE event.contributor = :contributor")
                .setParameter("contributor", contributor)
                .getSingleResult();
    }
}
