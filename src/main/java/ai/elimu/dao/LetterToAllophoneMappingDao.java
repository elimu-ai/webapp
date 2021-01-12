package ai.elimu.dao;

import ai.elimu.model.content.Allophone;
import ai.elimu.model.content.Letter;
import ai.elimu.model.content.LetterToAllophoneMapping;
import java.util.List;

import org.springframework.dao.DataAccessException;

public interface LetterToAllophoneMappingDao extends GenericDao<LetterToAllophoneMapping> {
    
//    LetterToAllophoneMapping read(List<Letter> letters, List<Allophone> allophones) throws DataAccessException;
    
    List<LetterToAllophoneMapping> readAllOrderedByUsage() throws DataAccessException;
    
    List<LetterToAllophoneMapping> readAllOrderedByLettersLength() throws DataAccessException;
}
