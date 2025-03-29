package ai.elimu.dao.jpa;

import ai.elimu.dao.StoryBookLearningEventDao;
import ai.elimu.entity.admin.Application;
import ai.elimu.entity.analytics.StoryBookLearningEvent;
import ai.elimu.entity.content.StoryBook;

import java.util.Calendar;
import java.util.List;
import jakarta.persistence.NoResultException;
import org.springframework.dao.DataAccessException;

public class StoryBookLearningEventDaoJpa extends GenericDaoJpa<StoryBookLearningEvent> implements StoryBookLearningEventDao {

    @Override
    public List<StoryBookLearningEvent> readAllOrderedByTime() throws DataAccessException {
        return em.createQuery(
            "SELECT sble " +
            "FROM StoryBookLearningEvent sble " +
            "ORDER BY sble.timestamp DESC")
            .getResultList();
    }
    
    @Override
    public StoryBookLearningEvent read(Calendar timestamp, String androidId, Application application, StoryBook storyBook) throws DataAccessException {
        try {
            return (StoryBookLearningEvent) em.createQuery(
                "SELECT sble " +
                "FROM StoryBookLearningEvent sble " +
                "WHERE sble.timestamp = :timestamp " +
                "AND sble.androidId = :androidId " + 
                "AND sble.application = :application " + 
                "AND sble.storyBook = :storyBook")
                .setParameter("timestamp", timestamp)
                .setParameter("androidId", androidId)
                .setParameter("application", application)
                .setParameter("storyBook", storyBook)
                .getSingleResult();
        } catch (NoResultException e) {
            logger.info("StoryBookLearningEvent (" + timestamp.getTimeInMillis() + ", " + androidId + ", " + application.getPackageName() + ", " + storyBook.getId() + ") was not found");
            return null;
        }
    }
}
