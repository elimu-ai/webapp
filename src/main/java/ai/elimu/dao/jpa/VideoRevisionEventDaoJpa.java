package ai.elimu.dao.jpa;

import java.util.List;
import ai.elimu.dao.VideoRevisionEventDao;
import ai.elimu.model.content.multimedia.Video;
import ai.elimu.model.contributor.VideoRevisionEvent;
import org.springframework.dao.DataAccessException;

public class VideoRevisionEventDaoJpa extends GenericDaoJpa<VideoRevisionEvent> implements VideoRevisionEventDao {

    @Override
    public List<VideoRevisionEvent> readAll(Video video) throws DataAccessException {
        return em.createQuery(
            "SELECT event " +
            "FROM VideoRevisionEvent event " +
            "WHERE event.video = :video " +
            "ORDER BY event.calendar DESC")
            .setParameter("video", video)
            .getResultList();
    }
}
