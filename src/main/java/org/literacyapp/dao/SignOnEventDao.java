package org.literacyapp.dao;

import java.util.List;
import org.literacyapp.model.contributor.SignOnEvent;
import org.springframework.dao.DataAccessException;

public interface SignOnEventDao extends GenericDao<SignOnEvent> {
    
    List<SignOnEvent> readAllOrderedDesc() throws DataAccessException;
}
