package org.literacyapp.dao;

import java.util.List;
import org.literacyapp.model.content.Word;
import org.literacyapp.model.contributor.WordRevisionEvent;
import org.springframework.dao.DataAccessException;

public interface WordRevisionEventDao extends GenericDao<WordRevisionEvent> {
    
    List<WordRevisionEvent> readAll(Word word) throws DataAccessException;
}
