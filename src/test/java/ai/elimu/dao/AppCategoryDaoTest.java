package ai.elimu.dao;

import java.util.List;
import org.apache.log4j.Logger;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import ai.elimu.model.project.AppCategory;
import ai.elimu.model.project.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/applicationContext-jpa.xml")
public class AppCategoryDaoTest {
    
    private Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private AppCategoryDao appCategoryDao;
    
    @Autowired
    private ProjectDao projectDao;
    
    @Test
    public void testReadAll() {
        List<AppCategory> appCategories = appCategoryDao.readAll();
        assertThat(appCategories.size(), is(0));
        
        AppCategory appCategory = new AppCategory();
        appCategoryDao.create(appCategory);
        appCategories.add(appCategory);
        Project project = new Project();
        project.setAppCategories(appCategories);
        projectDao.create(project);
        assertThat(appCategories.size(), is(1));
    }
}
