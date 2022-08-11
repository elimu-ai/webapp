package ai.elimu.dao.jpa;

import ai.elimu.dao.StoryBookChapterDao;
import java.util.List;

import ai.elimu.model.content.multimedia.Image;
import org.springframework.dao.DataAccessException;

import ai.elimu.model.content.StoryBook;
import ai.elimu.model.content.StoryBookChapter;

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

    @Override
    public List<StoryBookChapter> readAllWithImage(Image image) throws DataAccessException {
        return em.createQuery(
                        "SELECT ch " +
                                "FROM StoryBookChapter ch " +
                                "WHERE ch.image.id = :image ")
                .setParameter("image", image.getId())
                .getResultList();

    }

}
