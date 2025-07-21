package ai.elimu.dao;

import java.util.List;
import org.springframework.dao.DataAccessException;

import ai.elimu.entity.content.Word;
import ai.elimu.entity.contributor.Contributor;
import ai.elimu.entity.contributor.WordContributionEvent;

public interface WordContributionEventDao extends GenericDao<WordContributionEvent> {
    
    List<WordContributionEvent> readAllOrderedByTimeDesc() throws DataAccessException;
    
    List<WordContributionEvent> readAll(Word word) throws DataAccessException;
    
    List<WordContributionEvent> readAll(Contributor contributor) throws DataAccessException;
    
    Long readCount(Contributor contributor) throws DataAccessException;
}
