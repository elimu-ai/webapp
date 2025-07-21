package ai.elimu.dao;

import java.util.List;
import org.springframework.dao.DataAccessException;

import ai.elimu.entity.content.multimedia.Video;
import ai.elimu.entity.contributor.Contributor;
import ai.elimu.entity.contributor.VideoContributionEvent;

public interface VideoContributionEventDao extends GenericDao<VideoContributionEvent> {
    
    List<VideoContributionEvent> readAll(Video video) throws DataAccessException;
    
    List<VideoContributionEvent> readAll(Contributor contributor) throws DataAccessException;
    
    Long readCount(Contributor contributor) throws DataAccessException;
}
