package ai.elimu.dao.jpa;

import ai.elimu.dao.LetterToAllophoneMappingDao;
import ai.elimu.model.content.Allophone;
import ai.elimu.model.content.Letter;
import ai.elimu.model.content.LetterToAllophoneMapping;
import java.util.List;
import javax.persistence.NoResultException;
import org.springframework.dao.DataAccessException;

public class LetterToAllophoneMappingDaoJpa extends GenericDaoJpa<LetterToAllophoneMapping> implements LetterToAllophoneMappingDao {

    @Override
    public LetterToAllophoneMapping read(Letter letter, List<Allophone> allophones) throws DataAccessException {
        try {
            return (LetterToAllophoneMapping) em.createQuery(
                "SELECT ltam " +
                "FROM LetterToAllophoneMapping ltam " +
                "WHERE ltam.letter = :letter " +
                "AND ltam.allophones = :allophones")
                .setParameter("letter", letter)
                .setParameter("allophones", allophones)
                .getSingleResult();
        } catch (NoResultException e) {
            logger.warn("LetterToAllophoneMapping was not found for Letter \"" + letter.getText() + "\"");
            return null;
        }
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
    public List<LetterToAllophoneMapping> readAllOrderedByLetterText() throws DataAccessException {
        return em.createQuery(
            "SELECT ltam " +
            "FROM LetterToAllophoneMapping ltam " +
            "ORDER BY ltam.letter.text")
            .getResultList();
    }
}
