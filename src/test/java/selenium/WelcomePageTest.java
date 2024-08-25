package selenium;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import selenium.util.DomainHelper;

public class WelcomePageTest {
    
    private final Logger logger = LogManager.getLogger();
    
    private WebDriver driver;

    @BeforeEach
    public void setUp() { 
        logger.info("setUp");

        ChromeOptions chromeOptions = new ChromeOptions();

        // Read "headless" property set on the command line: 
        //    mvn clean verify -P regression-testing-ui -D headless=true
        String headlessSystemProperty = System.getProperty("headless");
        logger.info("headlessSystemProperty: \"" + headlessSystemProperty + "\"");
        if ("true".equals(headlessSystemProperty)) {
            chromeOptions.addArguments("headless");
        }
        
        driver = new ChromeDriver(chromeOptions);

        driver.get(DomainHelper.getBaseUrl());
    }

    @AfterEach
    public void tearDown() {
        logger.info("tearDown");

        driver.quit();
    }

    @Test
    public void testWelcomePage() {
        logger.info("testWelcomePage");
        
        WelcomePage welcomePage = new WelcomePage(driver);
    }
}
