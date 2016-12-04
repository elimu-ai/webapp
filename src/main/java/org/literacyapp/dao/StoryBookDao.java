package org.literacyapp.dao;

import java.util.List;
import org.literacyapp.model.content.StoryBook;

import org.literacyapp.model.enums.Locale;

import org.springframework.dao.DataAccessException;

public interface StoryBookDao extends GenericDao<StoryBook> {
	
    StoryBook readByTitle(Locale locale, String title) throws DataAccessException;

    List<StoryBook> readAllOrdered(Locale locale) throws DataAccessException;
}
