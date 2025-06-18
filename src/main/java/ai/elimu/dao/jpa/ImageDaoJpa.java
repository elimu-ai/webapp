package ai.elimu.dao.jpa;

import java.util.List;

import jakarta.persistence.NoResultException;

import org.springframework.dao.DataAccessException;

import ai.elimu.dao.ImageDao;
import ai.elimu.entity.content.Word;
import ai.elimu.entity.content.multimedia.Image;

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
    public String readChecksumGitHub(String checksumMd5) throws DataAccessException {
        try {
            return (String) em.createQuery(
                "SELECT i.checksumGitHub " +
                "FROM Image i " +
                "WHERE i.checksumMd5 = :checksumMd5 " +
                "AND i.checksumGitHub IS NOT NULL " +
                "LIMIT 1")
                .setParameter("checksumMd5", checksumMd5)
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
    public List<Image> readAllOrderedById() throws DataAccessException {
        return em.createQuery(
                "SELECT i " +
                    "FROM Image i " +
                    "ORDER BY i.id")
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
