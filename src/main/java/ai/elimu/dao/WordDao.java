package ai.elimu.dao;

import java.util.List;

import ai.elimu.model.content.Word;
import ai.elimu.model.enums.Language;

import org.springframework.dao.DataAccessException;

public interface WordDao extends GenericDao<Word> {
	
    Word readByText(Language language, String text) throws DataAccessException;

    List<Word> readAllOrdered(Language language) throws DataAccessException;
    
    List<Word> readAllOrderedByUsage(Language language) throws DataAccessException;
    
    List<Word> readLatest(Language language) throws DataAccessException;
    
    List<Word> readInflections(Word word) throws DataAccessException;
    
    Long readCount(Language language) throws DataAccessException;
}
