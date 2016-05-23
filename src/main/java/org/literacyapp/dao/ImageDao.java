package org.literacyapp.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import org.literacyapp.model.Image;
import org.literacyapp.model.enums.Language;

public interface ImageDao extends GenericDao<Image> {
	
    Image read(String title, Language language) throws DataAccessException;

    List<Image> readAllOrdered(Language language) throws DataAccessException;
    
    List<Image> readLatest(Language language) throws DataAccessException;
}
