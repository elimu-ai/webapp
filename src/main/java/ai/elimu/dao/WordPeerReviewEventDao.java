package ai.elimu.dao;

import ai.elimu.entity.content.Word;
import ai.elimu.entity.contributor.Contributor;
import ai.elimu.entity.contributor.WordContributionEvent;
import ai.elimu.entity.contributor.WordPeerReviewEvent;
import java.util.List;
import org.springframework.dao.DataAccessException;

public interface WordPeerReviewEventDao extends GenericDao<WordPeerReviewEvent> {
    
    List<WordPeerReviewEvent> readAll(WordContributionEvent wordContributionEvent, Contributor contributor) throws DataAccessException;
    
    List<WordPeerReviewEvent> readAll(Word word) throws DataAccessException;
    
    List<WordPeerReviewEvent> readAll(Contributor contributor) throws DataAccessException;
    
    List<WordPeerReviewEvent> readAll(WordContributionEvent wordContributionEvent) throws DataAccessException;
    
    Long readCount(Contributor contributor) throws DataAccessException;
}
