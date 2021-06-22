package ai.elimu.dao.jpa;

import ai.elimu.dao.WordPeerReviewEventDao;
import ai.elimu.model.content.Word;
import ai.elimu.model.contributor.Contributor;
import ai.elimu.model.contributor.WordContributionEvent;
import ai.elimu.model.contributor.WordPeerReviewEvent;
import java.util.List;
import javax.persistence.NoResultException;
import org.springframework.dao.DataAccessException;

public class WordPeerReviewEventDaoJpa extends GenericDaoJpa<WordPeerReviewEvent> implements WordPeerReviewEventDao {
    
    @Override
    public WordPeerReviewEvent read(WordContributionEvent wordContributionEvent, Contributor contributor) throws DataAccessException {
        try {
            return (WordPeerReviewEvent) em.createQuery(
                "SELECT wpre " +
                "FROM WordPeerReviewEvent wpre " +
                "WHERE wpre.wordContributionEvent = :wordContributionEvent " +
                "AND wpre.contributor = :contributor")
                .setParameter("wordContributionEvent", wordContributionEvent)
                .setParameter("contributor", contributor)
                .getSingleResult();
        } catch (NoResultException e) {
            logger.warn("WordPeerReviewEvent was not found");
            return null;
        }
    }

    @Override
    public List<WordPeerReviewEvent> readAll(Word word) throws DataAccessException {
        return em.createQuery(
            "SELECT wpre " + 
            "FROM WordPeerReviewEvent wpre " +
            "WHERE wpre.wordContributionEvent.word = :word " + 
            "ORDER BY wpre.time DESC")
            .setParameter("word", word)
            .getResultList();
    }
    
    @Override
    public List<WordPeerReviewEvent> readAll(WordContributionEvent wordContributionEvent) throws DataAccessException {
        return em.createQuery(
            "SELECT wpre " + 
            "FROM WordPeerReviewEvent wpre " +
            "WHERE wpre.wordContributionEvent = :wordContributionEvent " + 
            "ORDER BY wpre.time DESC")
            .setParameter("wordContributionEvent", wordContributionEvent)
            .getResultList();
    }
}
