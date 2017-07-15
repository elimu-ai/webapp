package ai.elimu.dao;

import ai.elimu.dao.ApplicationDao;
import ai.elimu.dao.NumberDao;
import ai.elimu.dao.StudentDao;
import ai.elimu.dao.DeviceDao;
import ai.elimu.dao.NumberLearningEventDao;
import java.util.List;
import org.apache.log4j.Logger;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import ai.elimu.model.Device;
import ai.elimu.model.Student;
import ai.elimu.model.admin.Application;
import ai.elimu.model.analytics.NumberLearningEvent;
import ai.elimu.model.content.Number;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/applicationContext-jpa.xml")
public class NumberLearningEventDaoTest {
    
    private Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private NumberLearningEventDao numberLearningEventDao;
    
    @Autowired
    private NumberDao numberDao;
    
    @Autowired
    private DeviceDao deviceDao;
    
    @Autowired
    private ApplicationDao applicationDao;
    
    @Autowired
    private StudentDao studentDao;
    
    @Test
    public void testReadAllByDevice() {
        Device device = new Device();
        device.setDeviceId("576de45ad9e0b07cf66");
        deviceDao.create(device);
        
        List<NumberLearningEvent> numberLearningEvents = numberLearningEventDao.readAll(device);
        assertThat(numberLearningEvents.size(), is(0));
        
        Number number = new Number();
        number.setValue(1);
        numberDao.create(number);
        
        NumberLearningEvent numberLearningEvent = new NumberLearningEvent();
        numberLearningEvent.setDevice(device);
        numberLearningEvent.setNumber(number);
        numberLearningEventDao.create(numberLearningEvent);
        
        numberLearningEvents = numberLearningEventDao.readAll(device);
        assertThat(numberLearningEvents.size(), is(1));
        assertThat(numberLearningEvents.get(0).getNumber().getValue(), is(1));
    }
    
    @Test
    public void testReadAllByApplication() {
        Application application = new Application();
        application.setPackageName("ai.elimu.handwriting");
        applicationDao.create(application);
        
        List<NumberLearningEvent> numberLearningEvents = numberLearningEventDao.readAll(application);
        assertThat(numberLearningEvents.size(), is(0));
        
        NumberLearningEvent numberLearningEvent = new NumberLearningEvent();
        numberLearningEvent.setApplication(application);
        numberLearningEventDao.create(numberLearningEvent);
        
        numberLearningEvents = numberLearningEventDao.readAll(application);
        assertThat(numberLearningEvents.size(), is(1));
        assertThat(numberLearningEvents.get(0).getApplication().getPackageName(), is("ai.elimu.handwriting"));
    }
    
    @Test
    public void testReadAllByStudent() {
        Student student = new Student();
        student.setUniqueId("576de45ad9e0b07cf66_1");
        studentDao.create(student);
        
        List<NumberLearningEvent> numberLearningEvents = numberLearningEventDao.readAll(student);
        assertThat(numberLearningEvents.size(), is(0));
        
        NumberLearningEvent numberLearningEvent = new NumberLearningEvent();
        numberLearningEvent.setStudent(student);
        numberLearningEventDao.create(numberLearningEvent);
        
        numberLearningEvents = numberLearningEventDao.readAll(student);
        assertThat(numberLearningEvents.size(), is(1));
        assertThat(numberLearningEvents.get(0).getStudent().getUniqueId(), is("576de45ad9e0b07cf66_1"));
    }
}
