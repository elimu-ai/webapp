package ai.elimu.logic.converters;

import ai.elimu.dao.AppCollectionDao;
import org.apache.commons.lang.StringUtils;
import ai.elimu.model.project.AppCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

public class StringToAppCollectionConverter implements Converter<String, AppCollection> {

    @Autowired
    private AppCollectionDao appCollectionDao;
    
    /**
     * Convert AppCollection id to AppCollection entity
     */
    public AppCollection convert(String id) {
        if (StringUtils.isBlank(id)) {
            return null;
        } else {
            Long appCollectionId = Long.parseLong(id);
            AppCollection appCollection = appCollectionDao.read(appCollectionId);
            return appCollection;
        }
    }
}
