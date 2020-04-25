package ai.elimu.dao.jpa;

import java.util.List;
import javax.persistence.NoResultException;
import ai.elimu.dao.StoryBookDao;

import org.springframework.dao.DataAccessException;

import ai.elimu.model.content.StoryBook;
import ai.elimu.model.enums.ReadingLevel;

public class StoryBookDaoJpa extends GenericDaoJpa<StoryBook> implements StoryBookDao {

    @Override
    public StoryBook readByTitle(String title) throws DataAccessException {
        try {
            return (StoryBook) em.createQuery(
                "SELECT book " +
                "FROM StoryBook book " +
                "WHERE book.title = :title")
                .setParameter("title", title)
                .getSingleResult();
        } catch (NoResultException e) {
            logger.warn("StoryBook \"" + title + "\" was not found");
            return null;
        }
    }

    @Override
    public List<StoryBook> readAllOrdered() throws DataAccessException {
        return em.createQuery(
            "SELECT book " +
            "FROM StoryBook book " +
            "ORDER BY book.title")
            .getResultList();
    }
    
    @Override
    public List<StoryBook> readAllOrdered(ReadingLevel readingLevel) throws DataAccessException {
        return em.createQuery(
            "SELECT book " +
            "FROM StoryBook book " +
            "WHERE book.readingLevel = :readingLevel " +
            "ORDER BY book.title")
            .setParameter("readingLevel", readingLevel)
            .getResultList();
    }
    
    @Override
    public List<StoryBook> readAllUnleveled() throws DataAccessException {
        return em.createQuery(
            "SELECT book " +
            "FROM StoryBook book " +
            "WHERE book.readingLevel IS NULL " +
            "ORDER BY book.title")
            .getResultList();
    }
    
    @Override
    public Long readCount() throws DataAccessException {
        return (Long) em.createQuery(
            "SELECT COUNT(s) " +
            "FROM StoryBook s")
            .getSingleResult();
    }
}
