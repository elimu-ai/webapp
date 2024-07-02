package ai.elimu.dao.jpa;

import ai.elimu.dao.StoryBookLearningEventDao;
import ai.elimu.model.admin.Application;
import ai.elimu.model.analytics.StoryBookLearningEvent;
import ai.elimu.model.content.StoryBook;
import java.util.Calendar;
import java.util.List;
import javax.persistence.NoResultException;
import org.springframework.dao.DataAccessException;

public class StoryBookLearningEventDaoJpa extends GenericDaoJpa<StoryBookLearningEvent> implements StoryBookLearningEventDao {

    @Override
    public List<StoryBookLearningEvent> readAllOrderedByTime() throws DataAccessException {
        return em.createQuery(
            "SELECT sble " +
            "FROM StoryBookLearningEvent sble " +
            "ORDER BY sble.time DESC")
            .getResultList();
    }
    
    @Override
    public StoryBookLearningEvent read(Calendar time, String androidId, Application application, StoryBook storyBook) throws DataAccessException {
        try {
            return (StoryBookLearningEvent) em.createQuery(
                "SELECT sble " +
                "FROM StoryBookLearningEvent sble " +
                "WHERE sble.time = :time " +
                "AND sble.androidId = :androidId " + 
                "AND sble.application = :application " + 
                "AND sble.storyBook = :storyBook")
                .setParameter("time", time)
                .setParameter("androidId", androidId)
                .setParameter("application", application)
                .setParameter("storyBook", storyBook)
                .getSingleResult();
        } catch (NoResultException e) {
            logger.info("StoryBookLearningEvent (" + time.getTimeInMillis() + ", " + androidId + ", " + application.getPackageName() + ", " + storyBook.getId() + ") was not found");
            return null;
        }
    }
}
