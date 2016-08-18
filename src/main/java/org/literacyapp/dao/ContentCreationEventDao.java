package org.literacyapp.dao;

import java.util.Calendar;
import java.util.List;
import org.literacyapp.model.content.Content;
import org.literacyapp.model.contributor.ContentCreationEvent;
import org.literacyapp.model.enums.Locale;
import org.springframework.dao.DataAccessException;

public interface ContentCreationEventDao extends GenericDao<ContentCreationEvent> {
    
    List<ContentCreationEvent> readAll(Locale locale, int maxResults) throws DataAccessException;
    
    List<ContentCreationEvent> readAll(Calendar calendarFrom, Calendar calendarTo) throws DataAccessException;
    
    List<ContentCreationEvent> readAll(Content content) throws DataAccessException;
}
