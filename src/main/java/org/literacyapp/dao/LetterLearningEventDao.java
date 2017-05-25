package org.literacyapp.dao;

import java.util.List;
import org.literacyapp.model.Device;
import org.literacyapp.model.Student;
import org.literacyapp.model.admin.Application;
import org.literacyapp.model.analytics.LetterLearningEvent;

import org.springframework.dao.DataAccessException;

public interface LetterLearningEventDao extends GenericDao<LetterLearningEvent> {
	
    List<LetterLearningEvent> readAll(Device device) throws DataAccessException;
    
    List<LetterLearningEvent> readAll(Application application) throws DataAccessException;
    
    List<LetterLearningEvent> readAll(Student student) throws DataAccessException;
}
