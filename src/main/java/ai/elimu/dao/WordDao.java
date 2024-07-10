package ai.elimu.dao;

import java.util.List;

import ai.elimu.entity.content.Word;

import ai.elimu.model.v2.enums.content.WordType;
import org.springframework.dao.DataAccessException;

public interface WordDao extends GenericDao<Word> {
	
    Word readByText(String text) throws DataAccessException;

    Word readByTextAndType(String text, WordType wordType) throws DataAccessException;

    List<Word> readAllOrdered() throws DataAccessException;
    
    List<Word> readAllOrderedByUsage() throws DataAccessException;
    
    List<Word> readLatest() throws DataAccessException;
    
    List<Word> readInflections(Word word) throws DataAccessException;
}
