package org.literacyapp.util;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class VersionHelperTest {

    @Test
    public void testCreate() {
        String pomVersion = "1.0.0";
        assertThat(VersionHelper.getPomVersionAsInteger(pomVersion), is(100000000));
        
        pomVersion = "1.0.0-SNAPSHOT";
        assertThat(VersionHelper.getPomVersionAsInteger(pomVersion), is(100000000));
        
        pomVersion = "1.0.1";
        assertThat(VersionHelper.getPomVersionAsInteger(pomVersion), is(100000100));
        
        pomVersion = "1.1.0";
        assertThat(VersionHelper.getPomVersionAsInteger(pomVersion), is(100100000));
        
        pomVersion = "1.1.1";
        assertThat(VersionHelper.getPomVersionAsInteger(pomVersion), is(100100100));
        
        pomVersion = "1.0.11";
        assertThat(VersionHelper.getPomVersionAsInteger(pomVersion), is(100000110));
    }
}
