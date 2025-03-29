package ai.elimu.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import ai.elimu.entity.content.Letter;
import ai.elimu.entity.content.LetterSound;
import ai.elimu.entity.content.Sound;

public interface LetterSoundDao extends GenericDao<LetterSound> {
    
    LetterSound read(List<Letter> letters, List<Sound> sounds) throws DataAccessException;
    
    List<LetterSound> readAllOrderedByUsage() throws DataAccessException;

    List<LetterSound> readAllOrderedById() throws DataAccessException;
    
    List<LetterSound> readAllOrderedByLettersLength() throws DataAccessException;
}
