package ai.elimu.dao;

import ai.elimu.entity.admin.Application;
import ai.elimu.entity.analytics.LetterLearningEvent;
import ai.elimu.entity.content.Letter;
import java.util.Calendar;
import org.springframework.dao.DataAccessException;

public interface LetterLearningEventDao extends GenericDao<LetterLearningEvent> {
    
    LetterLearningEvent read(Calendar time, String androidId, Application application, Letter letter) throws DataAccessException;
}
