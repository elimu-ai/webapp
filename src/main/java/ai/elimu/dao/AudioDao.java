package ai.elimu.dao;

import ai.elimu.model.content.StoryBookParagraph;
import ai.elimu.model.content.Word;
import java.util.List;
import ai.elimu.model.content.multimedia.Audio;

import org.springframework.dao.DataAccessException;

public interface AudioDao extends GenericDao<Audio> {
    
    Audio readByTitle(String title) throws DataAccessException;
    
    Audio readByTranscription(String transcription) throws DataAccessException;

    List<Audio> readAllOrderedByTitle() throws DataAccessException;
    
    List<Audio> readAllOrderedByTimeLastUpdate() throws DataAccessException;
    
    List<Audio> readAll(Word word) throws DataAccessException;
    
    List<Audio> readAll(StoryBookParagraph storyBookParagraph) throws DataAccessException;
}
