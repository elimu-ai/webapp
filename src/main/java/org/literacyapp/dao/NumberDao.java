package org.literacyapp.dao;

import java.util.List;

import org.literacyapp.model.Number;
import org.literacyapp.model.enums.Language;

import org.springframework.dao.DataAccessException;

public interface NumberDao extends GenericDao<Number> {
	
    Number readByValue(Language language, Integer value) throws DataAccessException;

    List<Number> readAllOrdered(Language language) throws DataAccessException;
    
    List<Number> readLatest(Language language) throws DataAccessException;
}
