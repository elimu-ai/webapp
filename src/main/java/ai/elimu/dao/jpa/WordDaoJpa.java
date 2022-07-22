package ai.elimu.dao.jpa;

import java.util.List;
import javax.persistence.NoResultException;

import ai.elimu.model.v2.enums.content.WordType;
import org.springframework.dao.DataAccessException;

import ai.elimu.dao.WordDao;
import ai.elimu.model.content.Word;

public class WordDaoJpa extends GenericDaoJpa<Word> implements WordDao {

    @Override
    public Word readByText(String text) throws DataAccessException {
        try {
            return (Word) em.createQuery(
                "SELECT w " +
                "FROM Word w " +
                "WHERE w.text = :text")
                .setParameter("text", text)
                .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public Word readByTextAndType(String text, WordType wordType) throws DataAccessException {
        String query = "SELECT w " +
                "FROM Word w " +
                "WHERE w.text = :text ";
        if (wordType == null) {
            query += "AND w.wordType IS NULL AND :wordType IS NULL";
        } else {
            query += "AND w.wordType = :wordType";
        }
        try {
            return (Word) em.createQuery(query)
                    .setParameter("text", text)
                    .setParameter("wordType", wordType)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<Word> readAllOrdered() throws DataAccessException {
        return em.createQuery(
            "SELECT w " +
            "FROM Word w " +
            "ORDER BY w.text")
            .getResultList();
    }
    
    @Override
    public List<Word> readAllOrderedByUsage() throws DataAccessException {
        return em.createQuery(
            "SELECT w " +
            "FROM Word w " +
            "ORDER BY w.usageCount DESC, w.text")
            .getResultList();
    }

    @Override
    public List<Word> readLatest() throws DataAccessException {
        return em.createQuery(
            "SELECT w " +
            "FROM Word w " +
            "ORDER BY w.calendar DESC")
            .setMaxResults(10)
            .getResultList();
    }

    @Override
    public List<Word> readInflections(Word word) throws DataAccessException {
        return em.createQuery(
            "SELECT w " +
            "FROM Word w " +
            "WHERE w.rootWord = :word " +
            "ORDER BY w.text")
            .setParameter("word", word)
            .getResultList();
    }
}
