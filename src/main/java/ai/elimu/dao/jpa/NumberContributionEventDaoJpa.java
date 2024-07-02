package ai.elimu.dao.jpa;

import ai.elimu.model.contributor.NumberContributionEvent;
import ai.elimu.dao.NumberContributionEventDao;
import ai.elimu.model.content.Number;
import ai.elimu.model.contributor.Contributor;
import ai.elimu.model.contributor.WordContributionEvent;
import java.util.List;
import org.springframework.dao.DataAccessException;

public class NumberContributionEventDaoJpa extends GenericDaoJpa<NumberContributionEvent> implements NumberContributionEventDao {

    @Override
    public List<NumberContributionEvent> readAll(Number number) throws DataAccessException {
        return em.createQuery(
            "SELECT nce " + 
            "FROM NumberContributionEvent nce " +
            "WHERE nce.number = :number " + 
            "ORDER BY nce.time DESC")
            .setParameter("number", number)
            .getResultList();
    }
    
    @Override
    public List<NumberContributionEvent> readAll(Contributor contributor) throws DataAccessException {
        return em.createQuery(
            "SELECT nce " + 
            "FROM NumberContributionEvent nce " +
            "WHERE nce.contributor = :contributor " + 
            "ORDER BY nce.time DESC")
            .setParameter("contributor", contributor)
            .getResultList();
    }

    @Override
    public List<NumberContributionEvent> readMostRecent(int maxResults) throws DataAccessException {
        return em.createQuery(
            "SELECT nce " + 
            "FROM NumberContributionEvent nce " +
            "ORDER BY nce.time DESC")
            .setMaxResults(maxResults)
            .getResultList();
    }
    
    @Override
    public List<NumberContributionEvent> readMostRecentPerNumber() throws DataAccessException {
        return em.createQuery(
            "SELECT nce " + 
            "FROM NumberContributionEvent nce " +
            "WHERE nce.time IN (SELECT MAX(time) FROM NumberContributionEvent GROUP BY number_id) " + // TODO: replace with "NOT EXISTS"? - https://stackoverflow.com/a/25694562
            "ORDER BY nce.time ASC")
            .getResultList();
    }

    @Override
    public Long readCount(Contributor contributor) throws DataAccessException {
        return (Long) em.createQuery("SELECT COUNT(nce) " +
                "FROM NumberContributionEvent nce " +
                "WHERE nce.contributor = :contributor")
                .setParameter("contributor", contributor)
                .getSingleResult();
    }
}
