package ai.elimu.dao;

import java.util.List;
import ai.elimu.model.content.Word;
import ai.elimu.model.contributor.WordRevisionEvent;
import org.springframework.dao.DataAccessException;

public interface WordRevisionEventDao extends GenericDao<WordRevisionEvent> {
    
    List<WordRevisionEvent> readAll(Word word) throws DataAccessException;
}
