package ai.elimu.logic.converters;

import ai.elimu.dao.AppCategoryDao;
import org.apache.commons.lang.StringUtils;
import ai.elimu.model.project.AppCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

public class StringToAppCategoryConverter implements Converter<String, AppCategory> {

    @Autowired
    private AppCategoryDao appCategoryDao;
    
    /**
     * Convert AppCategory id to AppCategory
     */
    public AppCategory convert(String id) {
        if (StringUtils.isBlank(id)) {
            return null;
        } else {
            Long appCategoryId = Long.parseLong(id);
            AppCategory application = appCategoryDao.read(appCategoryId);
            return application;
        }
    }
}
