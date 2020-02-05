package ai.elimu.dao;

import ai.elimu.model.content.Emoji;
import java.util.List;
import org.springframework.dao.DataAccessException;

public interface EmojiDao extends GenericDao<Emoji> {
    
    Emoji readByGlyph(String glyph) throws DataAccessException;
    
    List<Emoji> readAllOrdered() throws DataAccessException;
}
