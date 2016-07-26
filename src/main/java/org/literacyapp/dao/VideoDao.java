package org.literacyapp.dao;

import java.util.List;
import org.literacyapp.model.content.multimedia.Video;

import org.springframework.dao.DataAccessException;

import org.literacyapp.model.enums.Locale;

public interface VideoDao extends GenericDao<Video> {
	
    Video read(String title, Locale locale) throws DataAccessException;

    List<Video> readAllOrdered(Locale locale) throws DataAccessException;
}
