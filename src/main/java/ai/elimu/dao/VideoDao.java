package ai.elimu.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import ai.elimu.entity.content.multimedia.Video;

public interface VideoDao extends GenericDao<Video> {
    
    Video read(String title) throws DataAccessException;

    Video readByChecksumMd5(String checksumMd5) throws DataAccessException;

    List<Video> readAllOrdered() throws DataAccessException;

    List<Video> readAllOrderedById() throws DataAccessException;
}
