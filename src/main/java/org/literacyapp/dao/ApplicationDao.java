package org.literacyapp.dao;

import java.util.List;
import org.literacyapp.model.Application;

import org.literacyapp.model.enums.Locale;

import org.springframework.dao.DataAccessException;

public interface ApplicationDao extends GenericDao<Application> {

    List<Application> readAll(Locale locale) throws DataAccessException;
}
