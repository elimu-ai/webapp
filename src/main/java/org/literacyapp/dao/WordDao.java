package org.literacyapp.dao;

import java.util.List;

import org.literacyapp.model.Word;
import org.literacyapp.model.enums.Locale;

import org.springframework.dao.DataAccessException;

public interface WordDao extends GenericDao<Word> {
	
    Word readByText(Locale locale, String text) throws DataAccessException;

    List<Word> readAllOrdered(Locale locale) throws DataAccessException;
    
    List<Word> readLatest(Locale locale) throws DataAccessException;
}
