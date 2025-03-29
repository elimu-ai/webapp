package ai.elimu.dao;

import java.util.Calendar;
import org.springframework.dao.DataAccessException;

import ai.elimu.entity.admin.Application;
import ai.elimu.entity.analytics.WordLearningEvent;
import ai.elimu.entity.content.Word;

public interface WordLearningEventDao extends GenericDao<WordLearningEvent> {
    
    WordLearningEvent read(Calendar time, String androidId, Application application, Word word) throws DataAccessException;
}
