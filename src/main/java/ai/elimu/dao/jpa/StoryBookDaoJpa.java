package ai.elimu.dao.jpa;

import java.util.List;
import javax.persistence.NoResultException;
import ai.elimu.dao.StoryBookDao;

import org.springframework.dao.DataAccessException;

import ai.elimu.model.content.StoryBook;
import ai.elimu.model.enums.Language;
import ai.elimu.model.enums.ReadingLevel;

public class StoryBookDaoJpa extends GenericDaoJpa<StoryBook> implements StoryBookDao {

    @Override
    public StoryBook readByTitle(Language language, String title) throws DataAccessException {
        try {
            return (StoryBook) em.createQuery(
                "SELECT book " +
                "FROM StoryBook book " +
                "WHERE book.language = :language " +
                "AND book.title = :title")
                .setParameter("language", language)
                .setParameter("title", title)
                .getSingleResult();
        } catch (NoResultException e) {
            logger.warn("StoryBook \"" + title + "\" was not found for language " + language);
            return null;
        }
    }

    @Override
    public List<StoryBook> readAllOrdered(Language language) throws DataAccessException {
        return em.createQuery(
            "SELECT book " +
            "FROM StoryBook book " +
            "WHERE book.language = :language " +
            "ORDER BY book.title")
            .setParameter("language", language)
            .getResultList();
    }
    
    @Override
    public List<StoryBook> readAllOrdered(Language language, ReadingLevel readingLevel) throws DataAccessException {
        return em.createQuery(
            "SELECT book " +
            "FROM StoryBook book " +
            "WHERE book.language = :language " +
            "AND book.readingLevel = :readingLevel " +
            "ORDER BY book.title")
            .setParameter("language", language)
            .setParameter("readingLevel", readingLevel)
            .getResultList();
    }
    
    @Override
    public List<StoryBook> readAllUnleveled(Language language) throws DataAccessException {
        return em.createQuery(
            "SELECT book " +
            "FROM StoryBook book " +
            "WHERE book.language = :language " +
            "AND book.readingLevel IS NULL " +
            "ORDER BY book.title")
            .setParameter("language", language)
            .getResultList();
    }
    
    @Override
    public Long readCount(Language language) throws DataAccessException {
        return (Long) em.createQuery(
            "SELECT COUNT(s) " +
            "FROM StoryBook s " +
            "WHERE s.language = :language")
            .setParameter("language", language)
            .getSingleResult();
    }
}
