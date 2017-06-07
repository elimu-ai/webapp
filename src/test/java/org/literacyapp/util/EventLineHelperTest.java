package org.literacyapp.util;

import java.util.Calendar;
import java.util.List;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class EventLineHelperTest {

    @Test
    public void testGetDeviceId() {
        String applicationOpenedEventLine = "id:163|deviceId:2312aff4939750ea|time:1496843219926|packageName:nyaqd.literacyapp.org|studentId:2312aff4939750ea_4";
        String deviceId = EventLineHelper.getDeviceId(applicationOpenedEventLine);
        assertThat(deviceId, is("2312aff4939750ea"));
    }
    
    @Test
    public void testGetTime() {
        String applicationOpenedEventLine = "id:163|deviceId:2312aff4939750ea|time:1496843219926|packageName:nyaqd.literacyapp.org|studentId:2312aff4939750ea_4";
        Calendar calendar = EventLineHelper.getTime(applicationOpenedEventLine);
        assertThat(calendar.getTime().toString(), is("Wed Jun 07 15:46:59 CEST 2017"));
    }
    
    @Test
    public void testGetPackageName() {
        String applicationOpenedEventLine = "id:163|deviceId:2312aff4939750ea|time:1496843219926|packageName:nyaqd.literacyapp.org|studentId:2312aff4939750ea_4";
        String deviceId = EventLineHelper.getPackageName(applicationOpenedEventLine);
        assertThat(deviceId, is("nyaqd.literacyapp.org"));
    }
    
    @Test
    public void testGetUniqueStudentId() {
        String applicationOpenedEventLine = "id:163|deviceId:2312aff4939750ea|time:1496843219926|packageName:nyaqd.literacyapp.org|studentId:2312aff4939750ea_4";
        String uniqueStudentId = EventLineHelper.getUniqueStudentId(applicationOpenedEventLine);
        assertThat(uniqueStudentId, is("2312aff4939750ea_4"));
    }
}
