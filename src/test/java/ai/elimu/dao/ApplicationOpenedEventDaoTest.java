package ai.elimu.dao;

import ai.elimu.dao.ApplicationDao;
import ai.elimu.dao.StudentDao;
import ai.elimu.dao.ApplicationOpenedEventDao;
import ai.elimu.dao.DeviceDao;
import java.util.List;
import org.apache.log4j.Logger;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import ai.elimu.model.Device;
import ai.elimu.model.Student;
import ai.elimu.model.admin.Application;
import ai.elimu.model.analytics.ApplicationOpenedEvent;
import ai.elimu.model.enums.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/applicationContext-jpa.xml")
public class ApplicationOpenedEventDaoTest {
    
    private Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private ApplicationOpenedEventDao applicationOpenedEventDao;
    
    @Autowired
    private DeviceDao deviceDao;
    
    @Autowired
    private ApplicationDao applicationDao;
    
    @Autowired
    private StudentDao studentDao;
    
    @Test
    public void testReadAllByLocale() {
        Locale locale = Locale.values()[(int) (Math.random() * Locale.values().length)];
        logger.info("locale: " + locale);
        
        List<ApplicationOpenedEvent> applicationOpenedEvents = applicationOpenedEventDao.readAll(locale);
        assertThat(applicationOpenedEvents.size(), is(0));
        
        Device device = new Device();
        device.setLocale(locale);
        deviceDao.create(device);
        
        ApplicationOpenedEvent applicationOpenedEvent1 = new ApplicationOpenedEvent();
        applicationOpenedEvent1.setDevice(device);
        applicationOpenedEventDao.create(applicationOpenedEvent1);
        
        ApplicationOpenedEvent applicationOpenedEvent2 = new ApplicationOpenedEvent();
        applicationOpenedEvent2.setDevice(device);
        applicationOpenedEventDao.create(applicationOpenedEvent2);
        
        applicationOpenedEvents = applicationOpenedEventDao.readAll(locale);
        assertThat(applicationOpenedEvents.size(), is(2));
    }
    
    @Test
    public void testReadAllByDevice() {
        Device device = new Device();
        device.setDeviceId("bcaef51200ac6d92bdd81");
        deviceDao.create(device);
        
        List<ApplicationOpenedEvent> applicationOpenedEvents = applicationOpenedEventDao.readAll(device);
        assertThat(applicationOpenedEvents.size(), is(0));
        
        ApplicationOpenedEvent applicationOpenedEvent1 = new ApplicationOpenedEvent();
        applicationOpenedEvent1.setDevice(device);
        applicationOpenedEventDao.create(applicationOpenedEvent1);
        
        ApplicationOpenedEvent applicationOpenedEvent2 = new ApplicationOpenedEvent();
        applicationOpenedEventDao.create(applicationOpenedEvent2);
        
        applicationOpenedEvents = applicationOpenedEventDao.readAll(device);
        assertThat(applicationOpenedEvents.size(), is(1));
    }
    
    @Test
    public void testReadAllByApplication() {
        String packageName = "ai.elimu.handwriting";
        
        List<ApplicationOpenedEvent> applicationOpenedEvents = applicationOpenedEventDao.readAll(packageName);
        assertThat(applicationOpenedEvents.size(), is(0));
        
        ApplicationOpenedEvent applicationOpenedEvent1 = new ApplicationOpenedEvent();
        applicationOpenedEvent1.setPackageName(packageName);
        applicationOpenedEventDao.create(applicationOpenedEvent1);
        
        ApplicationOpenedEvent applicationOpenedEvent2 = new ApplicationOpenedEvent();
        applicationOpenedEvent2.setPackageName(packageName);
        applicationOpenedEventDao.create(applicationOpenedEvent2);
        
        applicationOpenedEvents = applicationOpenedEventDao.readAll(packageName);
        assertThat(applicationOpenedEvents.size(), is(2));
        assertThat(applicationOpenedEvents.get(0).getPackageName(), is("ai.elimu.handwriting"));
        assertThat(applicationOpenedEvents.get(1).getPackageName(), is("ai.elimu.handwriting"));
    }
    
    @Test
    public void testReadAllByStudent() {
        Student student1 = new Student();
        student1.setUniqueId("bcaef51200ac6d92bdd81_1");
        studentDao.create(student1);
        
        List<ApplicationOpenedEvent> applicationOpenedEvents = applicationOpenedEventDao.readAll(student1);
        assertThat(applicationOpenedEvents.size(), is(0));
        
        ApplicationOpenedEvent applicationOpenedEvent1 = new ApplicationOpenedEvent();
        applicationOpenedEvent1.setStudent(student1);
        applicationOpenedEventDao.create(applicationOpenedEvent1);
        
        ApplicationOpenedEvent applicationOpenedEvent2 = new ApplicationOpenedEvent();
        applicationOpenedEvent2.setStudent(student1);
        applicationOpenedEventDao.create(applicationOpenedEvent2);
        
        applicationOpenedEvents = applicationOpenedEventDao.readAll(student1);
        assertThat(applicationOpenedEvents.size(), is(2));
        assertThat(applicationOpenedEvents.get(0).getStudent().getUniqueId(), is("bcaef51200ac6d92bdd81_1"));
        assertThat(applicationOpenedEvents.get(1).getStudent().getUniqueId(), is("bcaef51200ac6d92bdd81_1"));
        
        Student student2 = new Student();
        student2.setUniqueId("bcaef51200ac6d92bdd81_2");
        studentDao.create(student2);
        
        applicationOpenedEvents = applicationOpenedEventDao.readAll(student1);
        assertThat(applicationOpenedEvents.size(), is(2));
        assertThat(applicationOpenedEvents.get(0).getStudent().getUniqueId(), is("bcaef51200ac6d92bdd81_1"));
        assertThat(applicationOpenedEvents.get(1).getStudent().getUniqueId(), is("bcaef51200ac6d92bdd81_1"));
        
        applicationOpenedEvents = applicationOpenedEventDao.readAll(student2);
        assertThat(applicationOpenedEvents.size(), is(0));
        
        ApplicationOpenedEvent applicationOpenedEvent3 = new ApplicationOpenedEvent();
        applicationOpenedEvent3.setStudent(student2);
        applicationOpenedEventDao.create(applicationOpenedEvent3);
        
        applicationOpenedEvents = applicationOpenedEventDao.readAll(student2);
        assertThat(applicationOpenedEvents.size(), is(1));
        assertThat(applicationOpenedEvents.get(0).getStudent().getUniqueId(), is("bcaef51200ac6d92bdd81_2"));
        
        applicationOpenedEvents = applicationOpenedEventDao.readAll();
        assertThat(applicationOpenedEvents.size(), is(3));
        assertThat(applicationOpenedEvents.get(0).getStudent().getUniqueId(), is("bcaef51200ac6d92bdd81_1"));
        assertThat(applicationOpenedEvents.get(1).getStudent().getUniqueId(), is("bcaef51200ac6d92bdd81_1"));
        assertThat(applicationOpenedEvents.get(2).getStudent().getUniqueId(), is("bcaef51200ac6d92bdd81_2"));
    }
}
