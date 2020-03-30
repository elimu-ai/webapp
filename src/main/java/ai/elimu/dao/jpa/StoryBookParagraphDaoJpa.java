package ai.elimu.dao.jpa;

import ai.elimu.dao.StoryBookParagraphDao;
import java.util.List;

import org.springframework.dao.DataAccessException;

import ai.elimu.model.content.StoryBookChapter;
import ai.elimu.model.content.StoryBookParagraph;

public class StoryBookParagraphDaoJpa extends GenericDaoJpa<StoryBookParagraph> implements StoryBookParagraphDao {

    @Override
    public List<StoryBookParagraph> readAll(StoryBookChapter storyBookChapter) throws DataAccessException {
        return em.createQuery(
            "SELECT paragraph " +
            "FROM StoryBookParagraph paragraph " +
            "WHERE paragraph.storyBookChapter = :storyBookChapter " +
            "ORDER BY paragraph.sortOrder")
            .setParameter("storyBookChapter", storyBookChapter)
            .getResultList();
    }
}
