package ai.elimu.dao.jpa;

import java.util.List;

import javax.persistence.NoResultException;
import ai.elimu.dao.VideoDao;

import org.springframework.dao.DataAccessException;

import ai.elimu.model.content.multimedia.Video;

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
    public List<Video> readAllOrdered() throws DataAccessException {
        return em.createQuery(
            "SELECT v " +
            "FROM Video v " +
            "ORDER BY v.title")
            .getResultList();
    }
}
