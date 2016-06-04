package org.literacyapp.dao.jpa;

import java.util.List;
import javax.persistence.NoResultException;

import org.springframework.dao.DataAccessException;

import org.literacyapp.dao.WordDao;
import org.literacyapp.model.Word;
import org.literacyapp.model.enums.Language;

public class WordDaoJpa extends GenericDaoJpa<Word> implements WordDao {

    @Override
    public Word readByText(Language language, String text) throws DataAccessException {
        try {
            return (Word) em.createQuery(
                "SELECT w " +
                "FROM Word w " +
                "WHERE w.language = :language " +
                "AND w.text = :text")
                .setParameter("language", language)
                .setParameter("text", text)
                .getSingleResult();
        } catch (NoResultException e) {
            logger.warn("Word \"" + text + "\" was not found for language " + language);
            return null;
        }
    }

    @Override
    public List<Word> readAllOrdered(Language language) throws DataAccessException {
        return em.createQuery(
            "SELECT w " +
            "FROM Word w " +
            "WHERE w.language = :language " +
            "ORDER BY w.text")
            .setParameter("language", language)
            .getResultList();
    }

    @Override
    public List<Word> readLatest(Language language) throws DataAccessException {
        return em.createQuery(
            "SELECT w " +
            "FROM Word w " +
            "WHERE w.language = :language " +
            "ORDER BY w.calendar DESC")
            .setParameter("language", language)
            .setMaxResults(10)
            .getResultList();
    }
}
