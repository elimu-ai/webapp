package ai.elimu.logic.converters;

import org.apache.commons.lang.StringUtils;
import ai.elimu.dao.ProjectDao;
import ai.elimu.model.project.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

public class StringToProjectConverter implements Converter<String, Project> {

    @Autowired
    private ProjectDao projectDao;
    
    /**
     * Convert Project id to Project entity
     */
    public Project convert(String id) {
        if (StringUtils.isBlank(id)) {
            return null;
        } else {
            Long projectId = Long.parseLong(id);
            Project project = projectDao.read(projectId);
            return project;
        }
    }
}
