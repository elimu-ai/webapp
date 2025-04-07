package ai.elimu.dao.jpa;

import ai.elimu.dao.StoryBookParagraphDao;
import ai.elimu.entity.content.StoryBookChapter;
import ai.elimu.entity.content.StoryBookParagraph;

import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.dao.DataAccessException;

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

    @Override
    public List<StoryBookParagraph> readAllContainingWord(String wordText) throws DataAccessException {
        return em.createQuery(
            "SELECT paragraph " +
            "FROM StoryBookParagraph paragraph " +
            "WHERE paragraph.originalText LIKE '% " + StringEscapeUtils.escapeSql(wordText) + " %' " +
            "ORDER BY paragraph.storyBookChapter.id, paragraph.sortOrder")
            .getResultList();
    }
}
