package ai.elimu.dao;

import ai.elimu.model.content.multimedia.Image;
import ai.elimu.model.contributor.Contributor;
import ai.elimu.model.contributor.ImageContributionEvent;
import java.util.List;
import org.springframework.dao.DataAccessException;

public interface ImageContributionEventDao extends GenericDao<ImageContributionEvent> {
    
    List<ImageContributionEvent> readAll(Image image) throws DataAccessException;
    
    List<ImageContributionEvent> readAll(Contributor contributor) throws DataAccessException;

    Long readCount(Contributor contributor) throws DataAccessException;

    void deleteAllEventsForImage(Image image);
}
