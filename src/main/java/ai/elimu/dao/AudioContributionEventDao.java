package ai.elimu.dao;

import ai.elimu.entity.content.multimedia.Audio;
import ai.elimu.entity.contributor.Contributor;
import ai.elimu.entity.contributor.AudioContributionEvent;
import java.util.List;
import org.springframework.dao.DataAccessException;

public interface AudioContributionEventDao extends GenericDao<AudioContributionEvent> {
    
    List<AudioContributionEvent> readAll(Audio audio) throws DataAccessException;
    
    List<AudioContributionEvent> readAll(Contributor contributor) throws DataAccessException;
    
    List<AudioContributionEvent> readMostRecent(int maxResults) throws DataAccessException;
    
    Long readCount(Contributor contributor) throws DataAccessException;
}
