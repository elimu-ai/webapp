package ai.elimu.dao.jpa;

import ai.elimu.model.contributor.LetterContributionEvent;
import ai.elimu.dao.LetterContributionEventDao;
import ai.elimu.model.content.Letter;
import ai.elimu.model.contributor.Contributor;
import java.util.List;
import org.springframework.dao.DataAccessException;

public class LetterContributionEventDaoJpa extends GenericDaoJpa<LetterContributionEvent> implements LetterContributionEventDao {
    
    @Override
    public List<LetterContributionEvent> readAllOrderedByTimeDesc() throws DataAccessException {
        return em.createQuery(
            "SELECT event " + 
            "FROM LetterContributionEvent event " +
            "ORDER BY event.timestamp DESC")
            .getResultList();
    }

    @Override
    public List<LetterContributionEvent> readAll(Letter letter) throws DataAccessException {
        return em.createQuery(
            "SELECT event " + 
            "FROM LetterContributionEvent event " +
            "WHERE event.letter = :letter " + 
            "ORDER BY event.timestamp DESC")
            .setParameter("letter", letter)
            .getResultList();
    }
    
    @Override
    public List<LetterContributionEvent> readAll(Contributor contributor) throws DataAccessException {
        return em.createQuery(
            "SELECT event " + 
            "FROM LetterContributionEvent event " +
            "WHERE event.contributor = :contributor " + 
            "ORDER BY event.timestamp DESC")
            .setParameter("contributor", contributor)
            .getResultList();
    }

    @Override
    public List<LetterContributionEvent> readMostRecent(int maxResults) throws DataAccessException {
        return em.createQuery(
            "SELECT event " + 
            "FROM LetterContributionEvent event " +
            "ORDER BY event.timestamp DESC")
            .setMaxResults(maxResults)
            .getResultList();
    }
    
    @Override
    public List<LetterContributionEvent> readMostRecentPerLetter() throws DataAccessException {
        return em.createQuery(
            "SELECT event " + 
            "FROM LetterContributionEvent event " +
            "WHERE event.timestamp IN (SELECT MAX(timestamp) FROM LetterContributionEvent GROUP BY letter_id) " + // TODO: replace with "NOT EXISTS"? - https://stackoverflow.com/a/25694562
            "ORDER BY event.timestamp ASC")
            .getResultList();
    }

    @Override
    public Long readCount(Contributor contributor) throws DataAccessException {
        return (Long) em.createQuery("SELECT COUNT(event) " +
                "FROM LetterContributionEvent event " +
                "WHERE event.contributor = :contributor")
                .setParameter("contributor", contributor)
                .getSingleResult();
    }
}
