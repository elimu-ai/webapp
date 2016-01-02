package org.literacyapp.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import org.literacyapp.Image;

public interface ImageDao extends GenericDao<Image> {
	
    Image read(String title) throws DataAccessException;

    List<Image> readAllOrdered() throws DataAccessException;
}
