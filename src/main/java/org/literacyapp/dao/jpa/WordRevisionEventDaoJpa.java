package org.literacyapp.dao.jpa;

import java.util.List;
import org.literacyapp.dao.WordRevisionEventDao;
import org.literacyapp.model.content.Word;
import org.literacyapp.model.contributor.WordRevisionEvent;
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
