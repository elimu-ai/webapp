package ai.elimu.dao.jpa;

import java.util.List;

import jakarta.persistence.NoResultException;
import ai.elimu.dao.VideoDao;
import ai.elimu.entity.content.multimedia.Video;

import org.springframework.dao.DataAccessException;

public class VideoDaoJpa extends GenericDaoJpa<Video> implements VideoDao {

    @Override
    public Video read(String title) throws DataAccessException {
        try {
            return (Video) em.createQuery(
                "SELECT v " +
                "FROM Video v " +
                "WHERE v.title = :title")
                .setParameter("title", title)
                .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public Video readByChecksumMd5(String checksumMd5) throws DataAccessException {
        try {
            return (Video) em.createQuery(
                "SELECT v " +
                "FROM Video v " +
                "WHERE v.checksumMd5 = :checksumMd5")
                .setParameter("checksumMd5", checksumMd5)
                .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<Video> readAllOrdered() throws DataAccessException {
        return em.createQuery(
            "SELECT v " +
            "FROM Video v " +
            "ORDER BY v.title")
            .getResultList();
    }

    @Override
    public List<Video> readAllOrderedById() throws DataAccessException {
        return em.createQuery(
                "SELECT v " +
                    "FROM Video v " +
                    "ORDER BY v.id")
            .getResultList();
    }
}
