package org.literacyapp.dao;

import java.util.List;
import org.literacyapp.model.content.multimedia.Video;
import org.literacyapp.model.contributor.VideoRevisionEvent;
import org.springframework.dao.DataAccessException;

public interface VideoRevisionEventDao extends GenericDao<VideoRevisionEvent> {
    
    List<VideoRevisionEvent> readAll(Video video) throws DataAccessException;
}
