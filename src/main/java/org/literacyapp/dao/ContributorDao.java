package org.literacyapp.dao;

import java.util.List;
import org.literacyapp.model.Contributor;

import org.springframework.dao.DataAccessException;

public interface ContributorDao extends GenericDao<Contributor> {
	
    Contributor read(String email) throws DataAccessException;
    
    Contributor readByProviderIdGitHub(String id) throws DataAccessException;
    
    List<Contributor> readAllOrderedDesc() throws DataAccessException;
}
