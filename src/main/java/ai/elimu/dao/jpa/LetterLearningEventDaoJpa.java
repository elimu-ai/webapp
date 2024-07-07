package ai.elimu.dao.jpa;

import ai.elimu.dao.LetterLearningEventDao;
import ai.elimu.entity.admin.Application;
import ai.elimu.entity.analytics.LetterLearningEvent;
import ai.elimu.entity.content.Letter;
import java.util.Calendar;
import javax.persistence.NoResultException;
import org.springframework.dao.DataAccessException;

public class LetterLearningEventDaoJpa extends GenericDaoJpa<LetterLearningEvent> implements LetterLearningEventDao {

    @Override
    public LetterLearningEvent read(Calendar time, String androidId, Application application, Letter letter) throws DataAccessException {
        try {
            return (LetterLearningEvent) em.createQuery(
                "SELECT lle " +
                "FROM LetterLearningEvent lle " +
                "WHERE lle.time = :time " +
                "AND lle.androidId = :androidId " + 
                "AND lle.application = :application " + 
                "AND lle.letter = :letter")
                .setParameter("time", time)
                .setParameter("androidId", androidId)
                .setParameter("application", application)
                .setParameter("letter", letter)
                .getSingleResult();
        } catch (NoResultException e) {
            logger.info("LetterLearningEvent (" + time.getTimeInMillis() + ", " + androidId + ", " + application.getPackageName() + ", \"" + letter.getText() + "\") was not found");
            return null;
        }
    }
}
