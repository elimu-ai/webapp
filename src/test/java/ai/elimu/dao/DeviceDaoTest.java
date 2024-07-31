package ai.elimu.dao;

import ai.elimu.model.Device;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringJUnitConfig(locations = {
    "file:src/main/webapp/WEB-INF/spring/applicationContext.xml",
    "file:src/main/webapp/WEB-INF/spring/applicationContext-jpa.xml"
})
public class DeviceDaoTest {

    @Autowired
    private DeviceDao deviceDao;

    @Test
    public void testRead() {
        deviceDao.create(getDevice("22"));
        deviceDao.create(getDevice("44"));

        assertEquals("22", deviceDao.read("22").getAndroidId());
        assertEquals("44", deviceDao.read("44").getAndroidId());
        assertNull(deviceDao.read("33"));
    }

    private Device getDevice(String androidId) {
        Device device = new Device();
        device.setAndroidId(androidId);
        return device;
    }
}
