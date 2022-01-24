package ai.elimu.dao;

import ai.elimu.model.content.Allophone;
import ai.elimu.model.content.Letter;
import ai.elimu.model.content.LetterSoundCorrespondence;
import java.util.List;

import org.springframework.dao.DataAccessException;

public interface LetterSoundCorrespondenceDao extends GenericDao<LetterSoundCorrespondence> {
    
    LetterSoundCorrespondence read(List<Letter> letters, List<Allophone> sounds) throws DataAccessException;
    
    List<LetterSoundCorrespondence> readAllOrderedByUsage() throws DataAccessException;
    
    List<LetterSoundCorrespondence> readAllOrderedByLettersLength() throws DataAccessException;
}
