package ai.elimu.dao;

import ai.elimu.entity.Device;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
        "file:src/main/webapp/WEB-INF/spring/applicationContext.xml",
        "file:src/main/webapp/WEB-INF/spring/applicationContext-jpa.xml"
})
public class DeviceDaoTest extends TestCase {

    @Autowired
    private DeviceDao deviceDao;

    @Test
    public void testRead() {
        deviceDao.create(getDevice("22"));
        deviceDao.create(getDevice("44"));

        assertTrue("22".equals(deviceDao.read("22").getDeviceId()));
        assertTrue("44".equals(deviceDao.read("44").getDeviceId()));
        assertNull(deviceDao.read("33"));
    }

    private Device getDevice(String deviceId) {
        Device device = new Device();
        device.setDeviceId(deviceId);
        return device;
    }
}
