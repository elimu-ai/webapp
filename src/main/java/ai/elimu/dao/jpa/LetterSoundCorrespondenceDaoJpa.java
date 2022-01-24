package ai.elimu.dao.jpa;

import ai.elimu.model.content.Allophone;
import ai.elimu.model.content.Letter;
import ai.elimu.model.content.LetterSoundCorrespondence;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.dao.DataAccessException;
import ai.elimu.dao.LetterSoundCorrespondenceDao;

public class LetterSoundCorrespondenceDaoJpa extends GenericDaoJpa<LetterSoundCorrespondence> implements LetterSoundCorrespondenceDao {

    @Override
    public LetterSoundCorrespondence read(List<Letter> letters, List<Allophone> sounds) throws DataAccessException {
        // TODO: implement usage of CriteriaQuery/CriteriaQuery
        
        String letterSoundCorrespondenceLetters = letters.stream().map(Letter::getText).collect(Collectors.joining());
        String letterSoundCorrespondenceAllophones = sounds.stream().map(Allophone::getValueIpa).collect(Collectors.joining());
        for (LetterSoundCorrespondence letterSoundCorrespondence : readAllOrderedByUsage()) {
            String lettersAsString = letterSoundCorrespondence.getLetters().stream().map(Letter::getText).collect(Collectors.joining());
            String allophonesAsString = letterSoundCorrespondence.getSounds().stream().map(Allophone::getValueIpa).collect(Collectors.joining());
            if (lettersAsString.equals(letterSoundCorrespondenceLetters) && allophonesAsString.equals(letterSoundCorrespondenceAllophones)) {
                return letterSoundCorrespondence;
            }
        }
        
        logger.warn("LetterSoundCorrespondence was not found for Letter(s)/Sound(s): " +
                    "\"" + letterSoundCorrespondenceLetters + "\"" +
                    " /" + letterSoundCorrespondenceAllophones + "/");
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
