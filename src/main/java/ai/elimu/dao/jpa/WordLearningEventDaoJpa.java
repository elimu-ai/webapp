package ai.elimu.dao.jpa;

import ai.elimu.dao.WordLearningEventDao;
import ai.elimu.entity.admin.Application;
import ai.elimu.entity.analytics.WordLearningEvent;
import ai.elimu.entity.content.Word;
import java.util.Calendar;
import javax.persistence.NoResultException;
import org.springframework.dao.DataAccessException;

public class WordLearningEventDaoJpa extends GenericDaoJpa<WordLearningEvent> implements WordLearningEventDao {

    @Override
    public WordLearningEvent read(Calendar time, String androidId, Application application, Word word) throws DataAccessException {
        try {
            return (WordLearningEvent) em.createQuery(
                "SELECT wle " +
                "FROM WordLearningEvent wle " +
                "WHERE wle.time = :time " +
                "AND wle.androidId = :androidId " + 
                "AND wle.application = :application " + 
                "AND wle.word = :word")
                .setParameter("time", time)
                .setParameter("androidId", androidId)
                .setParameter("application", application)
                .setParameter("word", word)
                .getSingleResult();
        } catch (NoResultException e) {
            logger.info("WordLearningEvent (" + time.getTimeInMillis() + ", " + androidId + ", " + application.getPackageName() + ", \"" + word.getText() + "\") was not found");
            return null;
        }
    }
}
