package ai.elimu.dao.jpa;

import ai.elimu.model.content.Sound;
import ai.elimu.model.content.Letter;
import ai.elimu.model.content.LetterSoundCorrespondence;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.dao.DataAccessException;
import ai.elimu.dao.LetterSoundCorrespondenceDao;

public class LetterSoundCorrespondenceDaoJpa extends GenericDaoJpa<LetterSoundCorrespondence> implements LetterSoundCorrespondenceDao {

    @Override
    public LetterSoundCorrespondence read(List<Letter> letters, List<Sound> sounds) throws DataAccessException {
        // TODO: implement usage of CriteriaQuery/CriteriaQuery
        
        String letterSoundCorrespondenceLetters = letters.stream().map(Letter::getText).collect(Collectors.joining());
        String letterSoundCorrespondenceSounds = sounds.stream().map(Sound::getValueIpa).collect(Collectors.joining());
        for (LetterSoundCorrespondence letterSoundCorrespondence : readAllOrderedByUsage()) {
            String lettersAsString = letterSoundCorrespondence.getLetters().stream().map(Letter::getText).collect(Collectors.joining());
            String soundsAsString = letterSoundCorrespondence.getSounds().stream().map(Sound::getValueIpa).collect(Collectors.joining());
            if (lettersAsString.equals(letterSoundCorrespondenceLetters) && soundsAsString.equals(letterSoundCorrespondenceSounds)) {
                return letterSoundCorrespondence;
            }
        }
        
        return null;
    }
    
    @Override
    public List<LetterSoundCorrespondence> readAllOrderedByUsage() throws DataAccessException {
        return em.createQuery(
            "SELECT lsc " +
            "FROM LetterSoundCorrespondence lsc " +
            "ORDER BY lsc.usageCount DESC")
            .getResultList();
    }
    
    @Override
    public List<LetterSoundCorrespondence> readAllOrderedByLettersLength() throws DataAccessException {
        return em.createQuery(
            "SELECT lsc " +
            "FROM LetterSoundCorrespondence lsc " +
            "ORDER BY lsc.letters.size DESC, lsc.usageCount DESC")
            .getResultList();
    }
}
