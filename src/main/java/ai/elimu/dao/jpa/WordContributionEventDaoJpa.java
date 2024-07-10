package ai.elimu.dao.jpa;

import ai.elimu.entity.contributor.WordContributionEvent;
import ai.elimu.dao.WordContributionEventDao;
import ai.elimu.entity.content.Word;
import ai.elimu.entity.contributor.Contributor;
import java.util.List;
import org.springframework.dao.DataAccessException;

public class WordContributionEventDaoJpa extends GenericDaoJpa<WordContributionEvent> implements WordContributionEventDao {
    
    @Override
    public List<WordContributionEvent> readAllOrderedByTimeDesc() throws DataAccessException {
        return em.createQuery(
            "SELECT wce " + 
            "FROM WordContributionEvent wce " +
            "ORDER BY wce.time DESC")
            .getResultList();
    }

    @Override
    public List<WordContributionEvent> readAll(Word word) throws DataAccessException {
        return em.createQuery(
            "SELECT wce " + 
            "FROM WordContributionEvent wce " +
            "WHERE wce.word = :word " + 
            "ORDER BY wce.time DESC")
            .setParameter("word", word)
            .getResultList();
    }
    
    @Override
    public List<WordContributionEvent> readAll(Contributor contributor) throws DataAccessException {
        return em.createQuery(
            "SELECT wce " + 
            "FROM WordContributionEvent wce " +
            "WHERE wce.contributor = :contributor " + 
            "ORDER BY wce.time DESC")
            .setParameter("contributor", contributor)
            .getResultList();
    }

    @Override
    public List<WordContributionEvent> readMostRecent(int maxResults) throws DataAccessException {
        return em.createQuery(
            "SELECT wce " + 
            "FROM WordContributionEvent wce " +
            "ORDER BY wce.time DESC")
            .setMaxResults(maxResults)
            .getResultList();
    }

    @Override
    public Long readCount(Contributor contributor) throws DataAccessException {
        return (Long) em.createQuery("SELECT COUNT(wce) " +
                "FROM WordContributionEvent wce " +
                "WHERE wce.contributor = :contributor")
                .setParameter("contributor", contributor)
                .getSingleResult();
    }
}
