package org.literacyapp.dao;

import java.util.List;
import org.literacyapp.model.contributor.ContentCreationEvent;
import org.springframework.dao.DataAccessException;

public interface ContentCreationEventDao extends GenericDao<ContentCreationEvent> {
    
    List<ContentCreationEvent> readAll(int maxResults) throws DataAccessException;
}
