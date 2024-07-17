package ai.elimu.dao.jpa;

import ai.elimu.model.contributor.SoundContributionEvent;
import ai.elimu.dao.SoundContributionEventDao;
import ai.elimu.model.content.Sound;
import ai.elimu.model.contributor.Contributor;
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
    public List<SoundContributionEvent> readMostRecent(int maxResults) throws DataAccessException {
        return em.createQuery(
            "SELECT event " + 
            "FROM SoundContributionEvent event " +
            "ORDER BY event.timestamp DESC")
            .setMaxResults(maxResults)
            .getResultList();
    }
    
    @Override
    public List<SoundContributionEvent> readMostRecentPerSound() throws DataAccessException {
        return em.createQuery(
            "SELECT event " + 
            "FROM SoundContributionEvent event " +
            "WHERE event.timestamp IN (SELECT MAX(time) FROM SoundContributionEvent GROUP BY sound_id) " + // TODO: replace with "NOT EXISTS"? - https://stackoverflow.com/a/25694562
            "ORDER BY event.timestamp ASC")
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
