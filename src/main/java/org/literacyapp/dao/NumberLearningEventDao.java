package org.literacyapp.dao;

import java.util.List;
import org.literacyapp.model.Device;
import org.literacyapp.model.Student;
import org.literacyapp.model.admin.Application;
import org.literacyapp.model.analytics.NumberLearningEvent;

import org.springframework.dao.DataAccessException;

public interface NumberLearningEventDao extends GenericDao<NumberLearningEvent> {
	
    List<NumberLearningEvent> readAll(Device device) throws DataAccessException;
    
    List<NumberLearningEvent> readAll(Application application) throws DataAccessException;
    
    List<NumberLearningEvent> readAll(Student student) throws DataAccessException;
}
