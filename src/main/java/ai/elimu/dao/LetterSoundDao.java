package ai.elimu.dao;

import ai.elimu.model.content.Sound;
import ai.elimu.model.content.Letter;
import ai.elimu.model.content.LetterSound;
import java.util.List;

import org.springframework.dao.DataAccessException;

public interface LetterSoundDao extends GenericDao<LetterSound> {
    
    LetterSound read(List<Letter> letters, List<Sound> sounds) throws DataAccessException;
    
    List<LetterSound> readAllOrderedByUsage() throws DataAccessException;
    
    List<LetterSound> readAllOrderedByLettersLength() throws DataAccessException;
}
