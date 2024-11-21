package ai.elimu.dao;

import java.util.List;
import ai.elimu.model.content.StoryBook;
import ai.elimu.model.content.StoryBookChapter;

import ai.elimu.model.content.multimedia.Image;
import org.springframework.dao.DataAccessException;

public interface StoryBookChapterDao extends GenericDao<StoryBookChapter> {
    
    List<StoryBookChapter> readAll(StoryBook storyBook) throws DataAccessException;

    List<StoryBookChapter> readAllWithImage(Image image) throws DataAccessException;

}
