package ai.elimu.dao;

import java.util.List;
import ai.elimu.entity.content.StoryBook;
import ai.elimu.entity.content.StoryBookChapter;

import org.springframework.dao.DataAccessException;

public interface StoryBookChapterDao extends GenericDao<StoryBookChapter> {
	
    List<StoryBookChapter> readAll(StoryBook storyBook) throws DataAccessException;
}
