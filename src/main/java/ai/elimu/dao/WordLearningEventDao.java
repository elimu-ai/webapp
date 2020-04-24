package ai.elimu.dao;

import ai.elimu.model.admin.Application;
import ai.elimu.model.analytics.WordLearningEvent;
import ai.elimu.model.content.Word;
import java.util.Calendar;
import org.springframework.dao.DataAccessException;

public interface WordLearningEventDao extends GenericDao<WordLearningEvent> {
    
    WordLearningEvent read(Calendar time, String androidId, Application application, Word word) throws DataAccessException;
}
