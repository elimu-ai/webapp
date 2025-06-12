package ai.elimu.dao;

import java.util.Calendar;
import java.util.List;

import org.springframework.dao.DataAccessException;

import ai.elimu.entity.analytics.NumberLearningEvent;

public interface NumberLearningEventDao extends GenericDao<NumberLearningEvent> {
    
    NumberLearningEvent read(Calendar time, String androidId, String packageName) throws DataAccessException;

    List<NumberLearningEvent> readAll(String androidId) throws DataAccessException;
}
