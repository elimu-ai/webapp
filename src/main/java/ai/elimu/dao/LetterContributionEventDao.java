package ai.elimu.dao;

import ai.elimu.entity.content.Letter;
import ai.elimu.entity.contributor.Contributor;
import ai.elimu.entity.contributor.LetterContributionEvent;
import java.util.List;
import org.springframework.dao.DataAccessException;

public interface LetterContributionEventDao extends GenericDao<LetterContributionEvent> {
    
    List<LetterContributionEvent> readAllOrderedByTimeDesc() throws DataAccessException;
    
    List<LetterContributionEvent> readAll(Letter letter) throws DataAccessException;
    
    List<LetterContributionEvent> readAll(Contributor contributor) throws DataAccessException;
    
    List<LetterContributionEvent> readMostRecent(int maxResults) throws DataAccessException;
    
    List<LetterContributionEvent> readMostRecentPerLetter() throws DataAccessException;
    
    Long readCount(Contributor contributor) throws DataAccessException;
}
