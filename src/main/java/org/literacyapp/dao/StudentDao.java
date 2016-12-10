package org.literacyapp.dao;

import java.util.List;
import org.literacyapp.model.Student;
import org.literacyapp.model.enums.Locale;

import org.springframework.dao.DataAccessException;

public interface StudentDao extends GenericDao<Student> {
	
    Student read(String uniqueId) throws DataAccessException;
    
    List<Student> readAll(Locale locale) throws DataAccessException;
}
