package ai.elimu.dao.jpa;

import java.util.List;
import jakarta.persistence.NoResultException;
import ai.elimu.dao.EmojiDao;

import org.springframework.dao.DataAccessException;

import ai.elimu.model.content.Emoji;
import ai.elimu.model.content.Word;

public class EmojiDaoJpa extends GenericDaoJpa<Emoji> implements EmojiDao {

    @Override
    public Emoji readByGlyph(String glyph) throws DataAccessException {
        try {
            return (Emoji) em.createQuery(
                "SELECT e " +
                "FROM Emoji e " +
                "WHERE e.glyph = :glyph")
                .setParameter("glyph", glyph)
                .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<Emoji> readAllOrdered() throws DataAccessException {
        return em.createQuery(
            "SELECT e " +
            "FROM Emoji e " +
            "ORDER BY e.glyph")
            .getResultList();
    }

    @Override
    public List<Emoji> readAllOrderedById() throws DataAccessException {
        return em.createQuery(
                "SELECT e " +
                    "FROM Emoji e " +
                    "ORDER BY e.id")
            .getResultList();
    }

    @Override
    public List<Emoji> readAllLabeled(Word word) throws DataAccessException {
        return em.createQuery(
            "SELECT e " +
            "FROM Emoji e " +
            "WHERE :word MEMBER OF e.words " + 
            "ORDER BY e.glyph")
            .setParameter("word", word)
            .getResultList();
    }
}
