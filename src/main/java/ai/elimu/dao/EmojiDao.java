package ai.elimu.dao;

import ai.elimu.model.content.Emoji;
import ai.elimu.model.enums.Language;
import java.util.List;
import org.springframework.dao.DataAccessException;

public interface EmojiDao extends GenericDao<Emoji> {
    
    Emoji readByGlyph(String glyph) throws DataAccessException;
    
    List<Emoji> readAllOrdered(Language language) throws DataAccessException;
    
    Long readCount(Language language) throws DataAccessException;
}
