package org.literacyapp.dao;

import org.literacyapp.model.Contributor;

import org.springframework.dao.DataAccessException;

public interface ContributorDao extends GenericDao<Contributor> {
	
    Contributor read(String email) throws DataAccessException;
}
