package ai.elimu.util;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class VersionHelperTest {

    @Test
    public void testCreate() {
        String pomVersion = "1.0.0";
        assertThat(VersionHelper.getPomVersionAsInteger(pomVersion), is(1000000));
        
        pomVersion = "1.0.0-SNAPSHOT";
        assertThat(VersionHelper.getPomVersionAsInteger(pomVersion), is(1000000));
        
        pomVersion = "1.0.1";
        assertThat(VersionHelper.getPomVersionAsInteger(pomVersion), is(1000001));
        
        pomVersion = "1.1.0";
        assertThat(VersionHelper.getPomVersionAsInteger(pomVersion), is(1001000));
        
        pomVersion = "1.1.1";
        assertThat(VersionHelper.getPomVersionAsInteger(pomVersion), is(1001001));
        
        pomVersion = "1.0.11";
        assertThat(VersionHelper.getPomVersionAsInteger(pomVersion), is(1000011));
        
        pomVersion = "11.0.11";
        assertThat(VersionHelper.getPomVersionAsInteger(pomVersion), is(11000011));
    }
}
