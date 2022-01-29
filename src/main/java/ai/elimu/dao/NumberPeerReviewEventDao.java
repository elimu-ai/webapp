package ai.elimu.dao;

import ai.elimu.model.content.Number;
import ai.elimu.model.contributor.Contributor;
import ai.elimu.model.contributor.NumberContributionEvent;
import ai.elimu.model.contributor.NumberPeerReviewEvent;
import java.util.List;
import org.springframework.dao.DataAccessException;

public interface NumberPeerReviewEventDao extends GenericDao<NumberPeerReviewEvent> {
    
    List<NumberPeerReviewEvent> readAll(NumberContributionEvent numberContributionEvent, Contributor contributor) throws DataAccessException;
    
    List<NumberPeerReviewEvent> readAll(Number number) throws DataAccessException;
    
    List<NumberPeerReviewEvent> readAll(Contributor contributor) throws DataAccessException;
    
    List<NumberPeerReviewEvent> readAll(NumberContributionEvent numberContributionEvent) throws DataAccessException;
    
    Long readCount(Contributor contributor) throws DataAccessException;
}
