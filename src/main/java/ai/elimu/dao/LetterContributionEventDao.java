package ai.elimu.dao;

import java.util.List;
import org.springframework.dao.DataAccessException;

import ai.elimu.entity.content.Letter;
import ai.elimu.entity.contributor.Contributor;
import ai.elimu.entity.contributor.LetterContributionEvent;

public interface LetterContributionEventDao extends GenericDao<LetterContributionEvent> {
    
    List<LetterContributionEvent> readAllOrderedByTimeDesc() throws DataAccessException;
    
    List<LetterContributionEvent> readAll(Letter letter) throws DataAccessException;
    
    List<LetterContributionEvent> readAll(Contributor contributor) throws DataAccessException;
    
    Long readCount(Contributor contributor) throws DataAccessException;
}
