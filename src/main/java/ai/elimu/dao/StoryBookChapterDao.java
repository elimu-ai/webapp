package ai.elimu.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import ai.elimu.entity.content.StoryBook;
import ai.elimu.entity.content.StoryBookChapter;

public interface StoryBookChapterDao extends GenericDao<StoryBookChapter> {
    
    List<StoryBookChapter> readAll(StoryBook storyBook) throws DataAccessException;
}
