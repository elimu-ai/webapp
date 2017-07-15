package ai.elimu.dao;

import ai.elimu.dao.StudentDao;
import ai.elimu.dao.DeviceDao;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.log4j.Logger;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.Ignore;

import org.junit.Test;
import org.junit.runner.RunWith;
import ai.elimu.model.Device;
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
    
    @Autowired
    private DeviceDao deviceDao;
    
    @Test(expected = JpaSystemException.class)
    public void testCreateStudentsWithNonUniqueIds() {        
        Student student1 = new Student();
        student1.setUniqueId("4113947bec18b7ad_1");
        studentDao.create(student1);
        
        Student student2 = new Student();
        student2.setUniqueId("4113947bec18b7ad_1");
        studentDao.create(student2);
    }

    @Test
    public void testStoreStudentWithOneDevice() {
        Set<Device> devices = new HashSet<>();
        
        Device device = new Device();
        device.setDeviceId("4113947bec18b7ad");
        deviceDao.create(device);
        devices.add(device);
        
        Student student = new Student();
        student.setUniqueId("4113947bec18b7ad_1");
        student.setDevices(devices);
        studentDao.create(student);
        
        Student studentStoredInDatabase = studentDao.read("4113947bec18b7ad_1");
        assertThat(studentStoredInDatabase.getDevices().size(), is(1));
    }
    
    @Test
    public void testStoreStudentWithTWoDevices() {
        Set<Device> devices = new HashSet<>();
        
        Device device1 = new Device();
        device1.setDeviceId("2223947bec18b7ad");
        deviceDao.create(device1);
        devices.add(device1);
        
        Device device2 = new Device();
        device2.setDeviceId("679b35bb2322c6e4");
        deviceDao.create(device2);
        devices.add(device2);
        
        Student student = new Student();
        student.setUniqueId("2223947bec18b7ad_1");
        student.setDevices(devices);
        studentDao.create(student);
        
        Student studentStoredInDatabase = studentDao.read("2223947bec18b7ad_1");
        assertThat(studentStoredInDatabase.getDevices().size(), is(2));
    }
}
