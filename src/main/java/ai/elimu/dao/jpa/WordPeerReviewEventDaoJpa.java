package ai.elimu.dao.jpa;

import ai.elimu.dao.WordPeerReviewEventDao;
import ai.elimu.model.content.Word;
import ai.elimu.model.contributor.Contributor;
import ai.elimu.model.contributor.WordContributionEvent;
import ai.elimu.model.contributor.WordPeerReviewEvent;
import java.util.List;
import org.springframework.dao.DataAccessException;

public class WordPeerReviewEventDaoJpa extends GenericDaoJpa<WordPeerReviewEvent> implements WordPeerReviewEventDao {
    
    @Override
    public List<WordPeerReviewEvent> readAll(WordContributionEvent wordContributionEvent, Contributor contributor) throws DataAccessException {
        return em.createQuery(
            "SELECT wpre " +
            "FROM WordPeerReviewEvent wpre " +
            "WHERE wpre.wordContributionEvent = :wordContributionEvent " +
            "AND wpre.contributor = :contributor " +
            "ORDER BY wpre.timestamp DESC")
            .setParameter("wordContributionEvent", wordContributionEvent)
            .setParameter("contributor", contributor)
            .getResultList();
    }

    @Override
    public List<WordPeerReviewEvent> readAll(Word word) throws DataAccessException {
        return em.createQuery(
            "SELECT wpre " + 
            "FROM WordPeerReviewEvent wpre " +
            "WHERE wpre.wordContributionEvent.word = :word " + 
            "ORDER BY wpre.timestamp DESC")
            .setParameter("word", word)
            .getResultList();
    }
    
    @Override
    public List<WordPeerReviewEvent> readAll(Contributor contributor) throws DataAccessException {
        return em.createQuery(
            "SELECT wpre " + 
            "FROM WordPeerReviewEvent wpre " +
            "WHERE wpre.contributor = :contributor " + 
            "ORDER BY wpre.timestamp DESC")
            .setParameter("contributor", contributor)
            .getResultList();
    }
    
    @Override
    public List<WordPeerReviewEvent> readAll(WordContributionEvent wordContributionEvent) throws DataAccessException {
        return em.createQuery(
            "SELECT wpre " + 
            "FROM WordPeerReviewEvent wpre " +
            "WHERE wpre.wordContributionEvent = :wordContributionEvent " + 
            "ORDER BY wpre.timestamp DESC")
            .setParameter("wordContributionEvent", wordContributionEvent)
            .getResultList();
    }
    
    @Override
    public Long readCount(Contributor contributor) throws DataAccessException {
        return (Long) em.createQuery("SELECT COUNT(wpre) " +
                "FROM WordPeerReviewEvent wpre " +
                "WHERE wpre.contributor = :contributor")
                .setParameter("contributor", contributor)
                .getSingleResult();
    }
}
