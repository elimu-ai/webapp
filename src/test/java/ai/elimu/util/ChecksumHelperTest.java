package ai.elimu.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ChecksumHelperTest  {

    @Test
    public void calculateMD5Test() {
        assertTrue("098f6bcd4621d373cade4e832627b4f6".equals(ChecksumHelper.calculateMD5("test".getBytes())));
        assertTrue("f696282aa4cd4f614aa995190cf442fe".equals(ChecksumHelper.calculateMD5("test11".getBytes())));
        assertTrue("79f2a1cb337cf2c19868048f754a781b".equals(ChecksumHelper.calculateMD5("calculateMD5Test".getBytes())));
        assertTrue("0b2e69e325a76637270d92d7416168ff".equals(ChecksumHelper.calculateMD5("calculateMD5Test11".getBytes())));
    }
}
