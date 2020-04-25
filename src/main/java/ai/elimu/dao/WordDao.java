package ai.elimu.dao;

import java.util.List;

import ai.elimu.model.content.Word;

import org.springframework.dao.DataAccessException;

public interface WordDao extends GenericDao<Word> {
	
    Word readByText(String text) throws DataAccessException;

    List<Word> readAllOrdered() throws DataAccessException;
    
    List<Word> readAllOrderedByUsage() throws DataAccessException;
    
    List<Word> readLatest() throws DataAccessException;
    
    List<Word> readInflections(Word word) throws DataAccessException;
    
    Long readCount() throws DataAccessException;
}
