package org.literacyapp.dao.jpa;

import java.util.Calendar;
import java.util.List;
import org.literacyapp.dao.ContentCreationEventDao;
import org.literacyapp.model.content.Content;
import org.literacyapp.model.contributor.ContentCreationEvent;
import org.literacyapp.model.enums.Locale;
import org.springframework.dao.DataAccessException;

public class ContentCreationEventDaoJpa extends GenericDaoJpa<ContentCreationEvent> implements ContentCreationEventDao {

    @Override
    public List<ContentCreationEvent> readAll(Locale locale, int maxResults) throws DataAccessException {
        return em.createQuery(
            "SELECT event " +
            "FROM ContentCreationEvent event " +
            "WHERE event.content.locale = :locale " +
            "ORDER BY event.calendar DESC")
            .setParameter("locale", locale)
            .setMaxResults(maxResults)
            .getResultList();
    }

    @Override
    public List<ContentCreationEvent> readAll(Calendar calendarFrom, Calendar calendarTo) throws DataAccessException {
        return em.createQuery(
            "SELECT event " +
            "FROM ContentCreationEvent event " +
            "WHERE event.calendar >= :calendarFrom " +
            "AND event.calendar < :calendarTo " +
            "ORDER BY event.calendar DESC")
            .setParameter("calendarFrom", calendarFrom)
            .setParameter("calendarTo", calendarTo)
            .getResultList();
    }

    @Override
    public List<ContentCreationEvent> readAll(Content content) throws DataAccessException {
        return em.createQuery(
            "SELECT event " +
            "FROM ContentCreationEvent event " +
            "WHERE event.content = :content " +
            "ORDER BY event.calendar DESC")
            .setParameter("content", content)
            .getResultList();
    }
}
