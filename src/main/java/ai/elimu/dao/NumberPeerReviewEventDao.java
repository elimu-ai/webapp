package ai.elimu.dao;

import java.util.List;
import org.springframework.dao.DataAccessException;

import ai.elimu.entity.content.Number;
import ai.elimu.entity.contributor.Contributor;
import ai.elimu.entity.contributor.NumberContributionEvent;
import ai.elimu.entity.contributor.NumberPeerReviewEvent;

public interface NumberPeerReviewEventDao extends GenericDao<NumberPeerReviewEvent> {
    
    List<NumberPeerReviewEvent> readAll(NumberContributionEvent numberContributionEvent, Contributor contributor) throws DataAccessException;
    
    List<NumberPeerReviewEvent> readAll(Number number) throws DataAccessException;
    
    List<NumberPeerReviewEvent> readAll(Contributor contributor) throws DataAccessException;
    
    List<NumberPeerReviewEvent> readAll(NumberContributionEvent numberContributionEvent) throws DataAccessException;
    
    Long readCount(Contributor contributor) throws DataAccessException;
}
