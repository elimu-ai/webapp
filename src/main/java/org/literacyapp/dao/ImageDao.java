package org.literacyapp.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import org.literacyapp.model.content.Image;
import org.literacyapp.model.enums.Locale;

public interface ImageDao extends GenericDao<Image> {
	
    Image read(String title, Locale locale) throws DataAccessException;

    List<Image> readAllOrdered(Locale locale) throws DataAccessException;
}
