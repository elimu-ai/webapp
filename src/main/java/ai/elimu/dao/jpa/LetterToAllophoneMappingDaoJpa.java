package ai.elimu.dao.jpa;

import ai.elimu.dao.LetterToAllophoneMappingDao;
import ai.elimu.model.content.LetterToAllophoneMapping;
import java.util.List;
import org.springframework.dao.DataAccessException;

public class LetterToAllophoneMappingDaoJpa extends GenericDaoJpa<LetterToAllophoneMapping> implements LetterToAllophoneMappingDao {

//    @Override
//    public LetterToAllophoneMapping read(List<Letter> letters, List<Allophone> allophones) throws DataAccessException {
//        try {
//            // TODO: use CriteriaQuery/CriteriaQuery
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
    
    @Override
    public List<LetterToAllophoneMapping> readAllOrderedByLettersLength() throws DataAccessException {
        return em.createQuery(
            "SELECT ltam " +
            "FROM LetterToAllophoneMapping ltam " +
            "ORDER BY ltam.letters.size DESC, ltam.usageCount DESC")
            .getResultList();
    }
}
