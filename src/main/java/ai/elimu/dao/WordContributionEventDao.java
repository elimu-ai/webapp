package ai.elimu.dao;

import ai.elimu.model.content.Word;
import ai.elimu.model.contributor.Contributor;
import ai.elimu.model.contributor.WordContributionEvent;
import java.util.List;
import org.springframework.dao.DataAccessException;

public interface WordContributionEventDao extends GenericDao<WordContributionEvent> {
    
    List<WordContributionEvent> readAllOrderedByTimeDesc() throws DataAccessException;
    
    List<WordContributionEvent> readAll(Word word) throws DataAccessException;
    
    List<WordContributionEvent> readAll(Contributor contributor) throws DataAccessException;
    
    List<WordContributionEvent> readMostRecent(int maxResults) throws DataAccessException;
    
    Long readCount(Contributor contributor) throws DataAccessException;
}
