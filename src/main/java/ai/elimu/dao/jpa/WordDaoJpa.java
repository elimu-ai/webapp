package ai.elimu.dao.jpa;

import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;

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
        Query queryBuilder;
        String query = "SELECT w " +
                "FROM Word w " +
                "WHERE w.text = :text ";

        if (wordType == null) {
            query += "AND w.wordType IS NULL";
            queryBuilder = em.createQuery(query)
                    .setParameter("text", text);
        } else {
            query += "AND w.wordType = :wordType";
            queryBuilder = em.createQuery(query)
                    .setParameter("text", text)
                    .setParameter("wordType", wordType);
        }

        try {
            return (Word) queryBuilder.getSingleResult();
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
