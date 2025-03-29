package ai.elimu.dao.jpa;

import ai.elimu.dao.NumberPeerReviewEventDao;
import ai.elimu.entity.content.Number;
import ai.elimu.entity.contributor.Contributor;
import ai.elimu.entity.contributor.NumberContributionEvent;
import ai.elimu.entity.contributor.NumberPeerReviewEvent;

import java.util.List;
import org.springframework.dao.DataAccessException;

public class NumberPeerReviewEventDaoJpa extends GenericDaoJpa<NumberPeerReviewEvent> implements NumberPeerReviewEventDao {
    
    @Override
    public List<NumberPeerReviewEvent> readAll(NumberContributionEvent numberContributionEvent, Contributor contributor) throws DataAccessException {
        return em.createQuery(
            "SELECT event " +
            "FROM NumberPeerReviewEvent event " +
            "WHERE event.numberContributionEvent = :numberContributionEvent " +
            "AND event.contributor = :contributor " +
            "ORDER BY event.timestamp DESC")
            .setParameter("numberContributionEvent", numberContributionEvent)
            .setParameter("contributor", contributor)
            .getResultList();
    }

    @Override
    public List<NumberPeerReviewEvent> readAll(Number number) throws DataAccessException {
        return em.createQuery(
            "SELECT event " + 
            "FROM NumberPeerReviewEvent event " +
            "WHERE event.numberContributionEvent.number = :number " + 
            "ORDER BY event.timestamp DESC")
            .setParameter("number", number)
            .getResultList();
    }
    
    @Override
    public List<NumberPeerReviewEvent> readAll(Contributor contributor) throws DataAccessException {
        return em.createQuery(
            "SELECT event " + 
            "FROM NumberPeerReviewEvent event " +
            "WHERE event.contributor = :contributor " + 
            "ORDER BY event.timestamp DESC")
            .setParameter("contributor", contributor)
            .getResultList();
    }
    
    @Override
    public List<NumberPeerReviewEvent> readAll(NumberContributionEvent numberContributionEvent) throws DataAccessException {
        return em.createQuery(
            "SELECT event " + 
            "FROM NumberPeerReviewEvent event " +
            "WHERE event.numberContributionEvent = :numberContributionEvent " + 
            "ORDER BY event.timestamp DESC")
            .setParameter("numberContributionEvent", numberContributionEvent)
            .getResultList();
    }
    
    @Override
    public Long readCount(Contributor contributor) throws DataAccessException {
        return (Long) em.createQuery("SELECT COUNT(event) " +
                "FROM NumberPeerReviewEvent event " +
                "WHERE event.contributor = :contributor")
                .setParameter("contributor", contributor)
                .getSingleResult();
    }
}
