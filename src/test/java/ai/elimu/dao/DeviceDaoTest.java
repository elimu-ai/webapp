package ai.elimu.dao;

import ai.elimu.dao.DeviceDao;
import java.util.HashSet;
import java.util.Set;
import org.apache.log4j.Logger;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import ai.elimu.model.Device;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/applicationContext-jpa.xml")
public class DeviceDaoTest {
    
    private Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private DeviceDao deviceDao;
    
    @Test
    public void testStoreNearbyDevices() {
        Device device = new Device();
        device.setDeviceId("aaabbbccc111222333");
        deviceDao.create(device);
        
        assertThat(device.getDevicesNearby().size(), is(0));
        
        Set<Device> nearbyDevices = new HashSet<>();
        
        Device nearbyDevice1 = new Device();
        nearbyDevice1.setDeviceId("dddeeefff444555666");
        deviceDao.create(nearbyDevice1);
        nearbyDevices.add(nearbyDevice1);
        
        Device nearbyDevice2 = new Device();
        nearbyDevice2.setDeviceId("ggghhhiii777888999");
        deviceDao.create(nearbyDevice2);
        nearbyDevices.add(nearbyDevice2);
        
        device.setDevicesNearby(nearbyDevices);
        deviceDao.update(device);
        
        assertThat(device.getDevicesNearby().size(), is(2));
    }
}
