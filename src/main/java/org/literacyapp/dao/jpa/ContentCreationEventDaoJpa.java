package org.literacyapp.dao.jpa;

import java.util.List;
import org.literacyapp.dao.ContentCreationEventDao;
import org.literacyapp.model.contributor.ContentCreationEvent;
import org.springframework.dao.DataAccessException;

public class ContentCreationEventDaoJpa extends GenericDaoJpa<ContentCreationEvent> implements ContentCreationEventDao {

    @Override
    public List<ContentCreationEvent> readAll(int maxResults) throws DataAccessException {
        return em.createQuery(
            "SELECT event " +
            "FROM ContentCreationEvent event " +
            "ORDER BY event.calendar DESC")
            .setMaxResults(maxResults)
            .getResultList();
    }
}
