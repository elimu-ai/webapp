package ai.elimu.dao;

import ai.elimu.model.content.Word;
import ai.elimu.model.contributor.WordContributionEvent;
import java.util.List;
import org.springframework.dao.DataAccessException;

public interface WordContributionEventDao extends GenericDao<WordContributionEvent> {
    
    List<WordContributionEvent> readAll(Word word) throws DataAccessException;
}
