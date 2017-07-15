package ai.elimu.dao.jpa;

import java.util.List;
import ai.elimu.dao.WordRevisionEventDao;
import ai.elimu.model.content.Word;
import ai.elimu.model.contributor.WordRevisionEvent;
import org.springframework.dao.DataAccessException;

public class WordRevisionEventDaoJpa extends GenericDaoJpa<WordRevisionEvent> implements WordRevisionEventDao {

    @Override
    public List<WordRevisionEvent> readAll(Word word) throws DataAccessException {
        return em.createQuery(
            "SELECT event " +
            "FROM WordRevisionEvent event " +
            "WHERE event.word = :word " +
            "ORDER BY event.calendar DESC")
            .setParameter("word", word)
            .getResultList();
    }
}
