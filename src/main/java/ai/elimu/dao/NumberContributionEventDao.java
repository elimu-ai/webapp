package ai.elimu.dao;

import ai.elimu.model.content.Number;
import ai.elimu.model.contributor.Contributor;
import ai.elimu.model.contributor.NumberContributionEvent;
import java.util.List;
import org.springframework.dao.DataAccessException;

public interface NumberContributionEventDao extends GenericDao<NumberContributionEvent> {
    
    List<NumberContributionEvent> readAll(Number number) throws DataAccessException;
    
    List<NumberContributionEvent> readAll(Contributor contributor) throws DataAccessException;
    
    List<NumberContributionEvent> readMostRecent(int maxResults) throws DataAccessException;
    
    List<NumberContributionEvent> readMostRecentPerNumber() throws DataAccessException;
    
    Long readCount(Contributor contributor) throws DataAccessException;
}
