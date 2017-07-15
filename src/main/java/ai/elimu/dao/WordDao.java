package ai.elimu.dao;

import java.util.List;

import ai.elimu.model.content.Word;
import org.literacyapp.model.enums.Locale;

import org.springframework.dao.DataAccessException;

public interface WordDao extends GenericDao<Word> {
	
    Word readByText(Locale locale, String text) throws DataAccessException;

    List<Word> readAllOrdered(Locale locale) throws DataAccessException;
    
    List<Word> readAllOrderedByUsage(Locale locale) throws DataAccessException;
    
    List<Word> readLatest(Locale locale) throws DataAccessException;
    
    Long readCount(Locale locale) throws DataAccessException;
}
