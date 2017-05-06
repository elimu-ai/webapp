package org.literacyapp.dao;

import java.util.List;
import org.literacyapp.model.content.Letter;

import org.literacyapp.model.enums.Locale;

import org.springframework.dao.DataAccessException;

public interface LetterDao extends GenericDao<Letter> {
    
    Letter readByText(Locale locale, String text) throws DataAccessException;
    
    List<Letter> readAllOrdered(Locale locale) throws DataAccessException;
}
