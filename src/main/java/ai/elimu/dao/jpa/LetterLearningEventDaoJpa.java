package ai.elimu.dao.jpa;

import ai.elimu.dao.LetterLearningEventDao;
import ai.elimu.model.admin.Application;
import ai.elimu.model.analytics.LetterLearningEvent;
import ai.elimu.model.content.Letter;
import java.util.Calendar;
import javax.persistence.NoResultException;
import org.springframework.dao.DataAccessException;

public class LetterLearningEventDaoJpa extends GenericDaoJpa<LetterLearningEvent> implements LetterLearningEventDao {

    @Override
    public LetterLearningEvent read(Calendar timestamp, String androidId, Application application, Letter letter) throws DataAccessException {
        try {
            return (LetterLearningEvent) em.createQuery(
                "SELECT lle " +
                "FROM LetterLearningEvent lle " +
                "WHERE lle.timestamp = :timestamp " +
                "AND lle.androidId = :androidId " + 
                "AND lle.application = :application " + 
                "AND lle.letter = :letter")
                .setParameter("timestamp", timestamp)
                .setParameter("androidId", androidId)
                .setParameter("application", application)
                .setParameter("letter", letter)
                .getSingleResult();
        } catch (NoResultException e) {
            logger.info("LetterLearningEvent (" + timestamp.getTimeInMillis() + ", " + androidId + ", " + application.getPackageName() + ", \"" + letter.getText() + "\") was not found");
            return null;
        }
    }
}
