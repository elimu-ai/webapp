package ai.elimu.dao.jpa;

import ai.elimu.model.contributor.NumberContributionEvent;
import ai.elimu.dao.NumberContributionEventDao;
import ai.elimu.model.content.Number;
import ai.elimu.model.contributor.Contributor;
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
    public List<NumberContributionEvent> readMostRecent(int maxResults) throws DataAccessException {
        return em.createQuery(
            "SELECT nce " + 
            "FROM NumberContributionEvent nce " +
            "ORDER BY nce.time DESC")
            .setMaxResults(maxResults)
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
