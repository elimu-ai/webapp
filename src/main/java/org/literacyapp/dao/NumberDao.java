package org.literacyapp.dao;

import java.util.List;

import org.literacyapp.model.content.Number;
import org.literacyapp.model.enums.Locale;

import org.springframework.dao.DataAccessException;

public interface NumberDao extends GenericDao<Number> {
	
    Number readByValue(Locale locale, Integer value) throws DataAccessException;

    List<Number> readAllOrdered(Locale locale) throws DataAccessException;
}
