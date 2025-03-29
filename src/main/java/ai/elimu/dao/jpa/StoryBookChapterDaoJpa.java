package ai.elimu.dao.jpa;

import ai.elimu.dao.StoryBookChapterDao;
import ai.elimu.entity.content.StoryBook;
import ai.elimu.entity.content.StoryBookChapter;

import java.util.List;

import org.springframework.dao.DataAccessException;

public class StoryBookChapterDaoJpa extends GenericDaoJpa<StoryBookChapter> implements StoryBookChapterDao {

    @Override
    public List<StoryBookChapter> readAll(StoryBook storyBook) throws DataAccessException {
        return em.createQuery(
            "SELECT ch " +
            "FROM StoryBookChapter ch " +
            "WHERE ch.storyBook = :storyBook " +
            "ORDER BY ch.sortOrder")
            .setParameter("storyBook", storyBook)
            .getResultList();
    }
}
