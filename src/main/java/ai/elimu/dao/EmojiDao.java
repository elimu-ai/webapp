package ai.elimu.dao;

import ai.elimu.entity.content.Emoji;
import ai.elimu.entity.content.Word;
import java.util.List;
import org.springframework.dao.DataAccessException;

public interface EmojiDao extends GenericDao<Emoji> {
    
    Emoji readByGlyph(String glyph) throws DataAccessException;
    
    List<Emoji> readAllOrdered() throws DataAccessException;
    
    /**
     * Fetch all Emojis that have been labeled by a Word.
     */
    List<Emoji> readAllLabeled(Word word) throws DataAccessException;
}
