package ai.elimu.dao.jpa;

import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.dao.DataAccessException;

import ai.elimu.model.content.multimedia.Image;
import ai.elimu.dao.ImageDao;
import ai.elimu.model.content.Word;
import ai.elimu.model.enums.Language;

public class ImageDaoJpa extends GenericDaoJpa<Image> implements ImageDao {

    @Override
    public Image read(String title, Language language) throws DataAccessException {
        try {
            return (Image) em.createQuery(
                "SELECT i " +
                "FROM Image i " +
                "WHERE i.title = :title " +
                "AND i.language = :language")
                .setParameter("title", title)
                .setParameter("language", language)
                .getSingleResult();
        } catch (NoResultException e) {
            logger.warn("Image \"" + title + "\" was not found for language " + language);
            return null;
        }
    }

    @Override
    public List<Image> readAllOrdered(Language language) throws DataAccessException {
        return em.createQuery(
            "SELECT i " +
            "FROM Image i " +
            "WHERE i.language = :language " +
            "ORDER BY i.title")
            .setParameter("language", language)
            .getResultList();
    }

    @Override
    public List<Image> readAllLabeled(Word word, Language language) throws DataAccessException {
        return em.createQuery(
            "SELECT i " +
            "FROM Image i " +
            "WHERE :word MEMBER OF i.words " + 
            "AND i.language = :language " +
            "ORDER BY i.title")
            .setParameter("word", word)
            .setParameter("language", language)
            .getResultList();
    }
    
    @Override
    public Long readCount(Language language) throws DataAccessException {
        return (Long) em.createQuery(
                "SELECT COUNT(i) " +
                "FROM Image i " +
                "WHERE i.language = :language")
                .setParameter("language", language)
                .getSingleResult();
    }
}
