package ai.elimu.dao;

import org.apache.log4j.Logger;

import org.junit.Test;
import org.junit.runner.RunWith;
import ai.elimu.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/applicationContext-jpa.xml")
public class StudentDaoTest {
    
    private Logger logger = Logger.getLogger(getClass());
	
    @Autowired
    private StudentDao studentDao;
    
    @Test(expected = JpaSystemException.class)
    public void testCreateStudentsWithNonUniqueIds() {        
        Student student1 = new Student();
        student1.setUniqueId("4113947bec18b7ad_1");
        studentDao.create(student1);
        
        Student student2 = new Student();
        student2.setUniqueId("4113947bec18b7ad_1");
        studentDao.create(student2);
    }
}
