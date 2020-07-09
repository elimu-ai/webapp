package ai.elimu.dao;

import ai.elimu.model.admin.Application;
import ai.elimu.model.analytics.LetterLearningEvent;
import ai.elimu.model.content.Letter;
import java.util.Calendar;
import org.springframework.dao.DataAccessException;

public interface LetterLearningEventDao extends GenericDao<LetterLearningEvent> {
    
    LetterLearningEvent read(Calendar time, String androidId, Application application, Letter letter) throws DataAccessException;
}
