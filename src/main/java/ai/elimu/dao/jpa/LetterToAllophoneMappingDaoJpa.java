package ai.elimu.dao.jpa;

import ai.elimu.dao.LetterToAllophoneMappingDao;
import ai.elimu.model.content.Allophone;
import ai.elimu.model.content.Letter;
import ai.elimu.model.content.LetterToAllophoneMapping;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.dao.DataAccessException;

public class LetterToAllophoneMappingDaoJpa extends GenericDaoJpa<LetterToAllophoneMapping> implements LetterToAllophoneMappingDao {

    @Override
    public LetterToAllophoneMapping read(List<Letter> letters, List<Allophone> allophones) throws DataAccessException {
        // TODO: implement usage of CriteriaQuery/CriteriaQuery
        
        String letterSoundCorrespondenceLetters = letters.stream().map(Letter::getText).collect(Collectors.joining());
        String letterSoundCorrespondenceAllophones = allophones.stream().map(Allophone::getValueIpa).collect(Collectors.joining());
        for (LetterToAllophoneMapping letterSoundCorrespondence : readAllOrderedByUsage()) {
            String lettersAsString = letterSoundCorrespondence.getLetters().stream().map(Letter::getText).collect(Collectors.joining());
            String allophonesAsString = letterSoundCorrespondence.getAllophones().stream().map(Allophone::getValueIpa).collect(Collectors.joining());
            if (lettersAsString.equals(letterSoundCorrespondenceLetters) && allophonesAsString.equals(letterSoundCorrespondenceAllophones)) {
                return letterSoundCorrespondence;
            }
        }
        
        logger.warn("LetterToAllophoneMapping was not found for Letter(s)/Allophone(s): " +
                    "\"" + letterSoundCorrespondenceLetters + "\"" +
                    " /" + letterSoundCorrespondenceAllophones + "/");
        return null;
    }
    
    @Override
    public List<LetterToAllophoneMapping> readAllOrderedByUsage() throws DataAccessException {
        return em.createQuery(
            "SELECT ltam " +
            "FROM LetterToAllophoneMapping ltam " +
            "ORDER BY ltam.usageCount DESC")
            .getResultList();
    }
    
    @Override
    public List<LetterToAllophoneMapping> readAllOrderedByLettersLength() throws DataAccessException {
        return em.createQuery(
            "SELECT ltam " +
            "FROM LetterToAllophoneMapping ltam " +
            "ORDER BY ltam.letters.size DESC, ltam.usageCount DESC")
            .getResultList();
    }
}
