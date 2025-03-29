package ai.elimu.dao.jpa;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.dao.DataAccessException;
import ai.elimu.dao.LetterSoundDao;
import ai.elimu.entity.content.Letter;
import ai.elimu.entity.content.LetterSound;
import ai.elimu.entity.content.Sound;

public class LetterSoundDaoJpa extends GenericDaoJpa<LetterSound> implements LetterSoundDao {

    @Override
    public LetterSound read(List<Letter> letters, List<Sound> sounds) throws DataAccessException {
        // TODO: implement usage of CriteriaQuery/CriteriaQuery
        
        String letterSoundLetters = letters.stream().map(Letter::getText).collect(Collectors.joining());
        String letterSoundSounds = sounds.stream().map(Sound::getValueIpa).collect(Collectors.joining());
        for (LetterSound letterSound : readAllOrderedByUsage()) {
            String lettersAsString = letterSound.getLetters().stream().map(Letter::getText).collect(Collectors.joining());
            String soundsAsString = letterSound.getSounds().stream().map(Sound::getValueIpa).collect(Collectors.joining());
            if (lettersAsString.equals(letterSoundLetters) && soundsAsString.equals(letterSoundSounds)) {
                return letterSound;
            }
        }
        
        return null;
    }
    
    @Override
    public List<LetterSound> readAllOrderedByUsage() throws DataAccessException {
        return em.createQuery(
            "SELECT lsc " +
            "FROM LetterSound lsc " +
            "ORDER BY lsc.usageCount DESC")
            .getResultList();
    }

    @Override
    public List<LetterSound> readAllOrderedById() throws DataAccessException {
        return em.createQuery(
                "SELECT lsc " +
                    "FROM LetterSound lsc " +
                    "ORDER BY lsc.id")
            .getResultList();
    }
    
    @Override
    public List<LetterSound> readAllOrderedByLettersLength() throws DataAccessException {
        return em.createQuery(
            "SELECT lsc " +
            "FROM LetterSound lsc " +
            "ORDER BY lsc.letters.size DESC, lsc.usageCount DESC")
            .getResultList();
    }
}
