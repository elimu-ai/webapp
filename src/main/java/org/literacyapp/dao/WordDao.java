package org.literacyapp.dao;

import java.util.List;

import org.literacyapp.model.Word;
import org.literacyapp.model.enums.Language;

import org.springframework.dao.DataAccessException;

public interface WordDao extends GenericDao<Word> {
	
    Word readByText(Language language, String text) throws DataAccessException;

    List<Word> readAllOrdered(Language language) throws DataAccessException;
    
    List<Word> readLatest(Language language) throws DataAccessException;
}
