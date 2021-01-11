package ai.elimu.dao.jpa;

import ai.elimu.dao.LetterToAllophoneMappingDao;
import ai.elimu.model.content.Allophone;
import ai.elimu.model.content.Letter;
import ai.elimu.model.content.LetterToAllophoneMapping;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.NoResultException;
import org.springframework.dao.DataAccessException;

public class LetterToAllophoneMappingDaoJpa extends GenericDaoJpa<LetterToAllophoneMapping> implements LetterToAllophoneMappingDao {

//    @Override
//    public LetterToAllophoneMapping read(List<Letter> letters, List<Allophone> allophones) throws DataAccessException {
//        try {
//            return (LetterToAllophoneMapping) em.createQuery(
//                "SELECT ltam " +
//                "FROM LetterToAllophoneMapping ltam " +
//                "WHERE ltam.letters = :letters " +
//                "AND ltam.allophones = :allophones")
//                .setParameter("letters", letters)
//                .setParameter("allophones", allophones)
//                .getSingleResult();
//        } catch (NoResultException e) {
//            logger.warn("LetterToAllophoneMapping was not found for Letter(s)/Allophone(s): " +
//                    "\"" + letters.stream().map(Letter::getText).collect(Collectors.joining()) + "\"" +
//                    " /" + allophones.stream().map(Allophone::getValueIpa).collect(Collectors.joining()) + "/");
//            return null;
//        }
//    }
    
    @Override
    public List<LetterToAllophoneMapping> readAllOrderedByUsage() throws DataAccessException {
        return em.createQuery(
            "SELECT ltam " +
            "FROM LetterToAllophoneMapping ltam " +
            "ORDER BY ltam.usageCount DESC")
            .getResultList();
    }
}
