package org.literacyapp.dao;

import java.util.List;
import org.literacyapp.model.Allophone;

import org.literacyapp.model.enums.Language;

import org.springframework.dao.DataAccessException;

public interface AllophoneDao extends GenericDao<Allophone> {
	
    Allophone readByValueIpa(Language language, String value) throws DataAccessException;

    List<Allophone> readAllOrdered(Language language) throws DataAccessException;
}
