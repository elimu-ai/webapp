package ai.elimu.logic.converters;

import ai.elimu.dao.AppGroupDao;
import org.apache.commons.lang.StringUtils;
import ai.elimu.model.project.AppGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

public class StringToAppGroupConverter implements Converter<String, AppGroup> {

    @Autowired
    private AppGroupDao appGroupDao;
    
    /**
     * Convert AppGroup id to AppGroup entity
     */
    public AppGroup convert(String id) {
        if (StringUtils.isBlank(id)) {
            return null;
        } else {
            Long appGroupId = Long.parseLong(id);
            AppGroup appGroup = appGroupDao.read(appGroupId);
            return appGroup;
        }
    }
}
