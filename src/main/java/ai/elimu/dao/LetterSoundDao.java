package ai.elimu.dao;

import ai.elimu.entity.content.Sound;
import ai.elimu.entity.content.Letter;
import ai.elimu.entity.content.LetterSoundCorrespondence;
import java.util.List;

import org.springframework.dao.DataAccessException;

public interface LetterSoundDao extends GenericDao<LetterSoundCorrespondence> {
    
    LetterSoundCorrespondence read(List<Letter> letters, List<Sound> sounds) throws DataAccessException;
    
    List<LetterSoundCorrespondence> readAllOrderedByUsage() throws DataAccessException;
    
    List<LetterSoundCorrespondence> readAllOrderedByLettersLength() throws DataAccessException;
}
