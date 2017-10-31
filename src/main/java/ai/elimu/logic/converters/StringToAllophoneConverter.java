package ai.elimu.logic.converters;

import org.apache.commons.lang.StringUtils;
import ai.elimu.dao.AllophoneDao;
import ai.elimu.model.content.Allophone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

public class StringToAllophoneConverter implements Converter<String, Allophone> {

    @Autowired
    private AllophoneDao allophoneDao;
    
    /**
     * Convert Allophone id to Allophone entity
     */
    public Allophone convert(String id) {
        if (StringUtils.isBlank(id)) {
            return null;
        } else {
            Long allophoneId = Long.parseLong(id);
            Allophone allophone = allophoneDao.read(allophoneId);
            return allophone;
        }
    }
}
