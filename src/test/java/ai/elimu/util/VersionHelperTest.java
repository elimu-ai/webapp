package ai.elimu.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VersionHelperTest {

    @Test
    public void testCreate() {
        String pomVersion = "1.0.0";
        assertEquals(1000000, VersionHelper.getPomVersionAsInteger(pomVersion));

        pomVersion = "1.0.0-SNAPSHOT";
        assertEquals(1000000, VersionHelper.getPomVersionAsInteger(pomVersion));

        pomVersion = "1.0.1";
        assertEquals(1000001, VersionHelper.getPomVersionAsInteger(pomVersion));

        pomVersion = "1.1.0";
        assertEquals(1001000, VersionHelper.getPomVersionAsInteger(pomVersion));

        pomVersion = "1.1.1";
        assertEquals(1001001, VersionHelper.getPomVersionAsInteger(pomVersion));

        pomVersion = "1.0.11";
        assertEquals(1000011, VersionHelper.getPomVersionAsInteger(pomVersion));

        pomVersion = "11.0.11";
        assertEquals(11000011, VersionHelper.getPomVersionAsInteger(pomVersion));
    }
}
