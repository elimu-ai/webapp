package ai.elimu.dao;

import java.util.List;
import ai.elimu.model.Device;
import ai.elimu.model.Student;
import ai.elimu.model.admin.Application;
import ai.elimu.model.analytics.NumberLearningEvent;

import org.springframework.dao.DataAccessException;

public interface NumberLearningEventDao extends GenericDao<NumberLearningEvent> {
	
    List<NumberLearningEvent> readAll(Device device) throws DataAccessException;
    
    List<NumberLearningEvent> readAll(Application application) throws DataAccessException;
    
    List<NumberLearningEvent> readAll(Student student) throws DataAccessException;
}
