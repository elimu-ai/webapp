package ai.elimu.dao.jpa;

import ai.elimu.model.contributor.AudioContributionEvent;
import ai.elimu.dao.AudioContributionEventDao;
import ai.elimu.model.content.multimedia.Audio;
import ai.elimu.model.contributor.Contributor;
import java.util.List;
import org.springframework.dao.DataAccessException;

public class AudioContributionEventDaoJpa extends GenericDaoJpa<AudioContributionEvent> implements AudioContributionEventDao {

    @Override
    public List<AudioContributionEvent> readAll(Audio audio) throws DataAccessException {
        return em.createQuery(
            "SELECT ace " + 
            "FROM AudioContributionEvent ace " +
            "WHERE ace.audio = :audio " + 
            "ORDER BY ace.timestamp DESC")
            .setParameter("audio", audio)
            .getResultList();
    }
    
    @Override
    public List<AudioContributionEvent> readAll(Contributor contributor) throws DataAccessException {
        return em.createQuery(
            "SELECT ace " + 
            "FROM AudioContributionEvent ace " +
            "WHERE ace.contributor = :contributor " + 
            "ORDER BY ace.timestamp DESC")
            .setParameter("contributor", contributor)
            .getResultList();
    }

    @Override
    public List<AudioContributionEvent> readMostRecent(int maxResults) throws DataAccessException {
        return em.createQuery(
            "SELECT ace " + 
            "FROM AudioContributionEvent ace " +
            "ORDER BY ace.timestamp DESC")
            .setMaxResults(maxResults)
            .getResultList();
    }

    @Override
    public Long readCount(Contributor contributor) throws DataAccessException {
        return (Long) em.createQuery("SELECT COUNT(ace) " +
                "FROM AudioContributionEvent ace " +
                "WHERE ace.contributor = :contributor")
                .setParameter("contributor", contributor)
                .getSingleResult();
    }
}
