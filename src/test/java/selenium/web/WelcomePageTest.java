package selenium.web;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.MethodRule;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import selenium.DomainHelper;
import selenium.ScreenshotOnFailureRule;

public class WelcomePageTest {
    
    private final Logger logger = Logger.getLogger(getClass());
    
    @Rule
    public MethodRule methodRule = new ScreenshotOnFailureRule();
    
    private WebDriver driver;

    @Before
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
