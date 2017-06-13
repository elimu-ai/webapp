package org.literacyapp.dao;

import java.util.List;
import org.literacyapp.model.content.Syllable;

import org.literacyapp.model.enums.Locale;

import org.springframework.dao.DataAccessException;

public interface SyllableDao extends GenericDao<Syllable> {
    
    Syllable readByText(Locale locale, String text) throws DataAccessException;
    
    List<Syllable> readAllOrdered(Locale locale) throws DataAccessException;
    
    Long readCount(Locale locale) throws DataAccessException;
}
