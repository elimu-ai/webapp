package selenium.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import selenium.DomainHelper;

public class WelcomePageTest {
    
    private final Logger logger = LogManager.getLogger();
    
    private WebDriver driver;

    @BeforeEach
    public void setUp() { 
        logger.info("setUp");
        
        // See https://www.selenium.dev/documentation/en/webdriver/driver_requirements/#chromium-chrome
        System.setProperty("webdriver.chrome.driver", "src/test/resources/selenium/chrome_80.0.3987.106/chromedriver_mac64/chromedriver");
    	driver = new ChromeDriver();
        driver.get(DomainHelper.getBaseUrl());
    }

    @Test
    public void testWelcomePage() {
        logger.info("testWelcomePage");
        
    	WelcomePage welcomePage = new WelcomePage(driver);
    }
}
