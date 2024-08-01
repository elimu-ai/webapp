package ai.elimu.dao.jpa;

import ai.elimu.dao.WordLearningEventDao;
import ai.elimu.model.admin.Application;
import ai.elimu.model.analytics.WordLearningEvent;
import ai.elimu.model.content.Word;
import java.util.Calendar;
import jakarta.persistence.NoResultException;
import org.springframework.dao.DataAccessException;

public class WordLearningEventDaoJpa extends GenericDaoJpa<WordLearningEvent> implements WordLearningEventDao {

    @Override
    public WordLearningEvent read(Calendar timestamp, String androidId, Application application, Word word) throws DataAccessException {
        try {
            return (WordLearningEvent) em.createQuery(
                "SELECT wle " +
                "FROM WordLearningEvent wle " +
                "WHERE wle.timestamp = :timestamp " +
                "AND wle.androidId = :androidId " + 
                "AND wle.application = :application " + 
                "AND wle.word = :word")
                .setParameter("timestamp", timestamp)
                .setParameter("androidId", androidId)
                .setParameter("application", application)
                .setParameter("word", word)
                .getSingleResult();
        } catch (NoResultException e) {
            logger.info("WordLearningEvent (" + timestamp.getTimeInMillis() + ", " + androidId + ", " + application.getPackageName() + ", \"" + word.getText() + "\") was not found");
            return null;
        }
    }
}
