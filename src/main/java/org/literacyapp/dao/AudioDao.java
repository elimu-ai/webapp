package org.literacyapp.dao;

import java.util.List;
import org.literacyapp.model.content.multimedia.Audio;

import org.springframework.dao.DataAccessException;

import org.literacyapp.model.enums.Locale;

public interface AudioDao extends GenericDao<Audio> {
	
    Audio read(String transcription, Locale locale) throws DataAccessException;

    List<Audio> readAllOrdered(Locale locale) throws DataAccessException;
    
    Long readCount(Locale locale) throws DataAccessException;
}
