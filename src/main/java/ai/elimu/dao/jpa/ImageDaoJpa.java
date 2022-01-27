package ai.elimu.dao.jpa;

import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.dao.DataAccessException;

import ai.elimu.model.content.multimedia.Image;
import ai.elimu.dao.ImageDao;
import ai.elimu.model.content.Word;

public class ImageDaoJpa extends GenericDaoJpa<Image> implements ImageDao {

    @Override
    public Image read(String title) throws DataAccessException {
        try {
            return (Image) em.createQuery(
                "SELECT i " +
                "FROM Image i " +
                "WHERE i.title = :title")
                .setParameter("title", title)
                .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<Image> readAllOrdered() throws DataAccessException {
        return em.createQuery(
            "SELECT i " +
            "FROM Image i " +
            "ORDER BY i.title")
            .getResultList();
    }

    @Override
    public List<Image> readAllLabeled(Word word) throws DataAccessException {
        return em.createQuery(
            "SELECT i " +
            "FROM Image i " +
            "WHERE :word MEMBER OF i.words " + 
            "ORDER BY i.title")
            .setParameter("word", word)
            .getResultList();
    }
}
