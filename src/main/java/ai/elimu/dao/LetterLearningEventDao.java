package ai.elimu.dao;

import java.util.List;
import ai.elimu.model.Device;
import ai.elimu.model.Student;
import ai.elimu.model.admin.Application;
import ai.elimu.model.analytics.LetterLearningEvent;

import org.springframework.dao.DataAccessException;

public interface LetterLearningEventDao extends GenericDao<LetterLearningEvent> {
	
    List<LetterLearningEvent> readAll(Device device) throws DataAccessException;
    
    List<LetterLearningEvent> readAll(Application application) throws DataAccessException;
    
    List<LetterLearningEvent> readAll(Student student) throws DataAccessException;
}
