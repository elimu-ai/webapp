package selenium.analytics;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import selenium.util.DomainHelper;

public class StoryBookLearningEventsPageTest {
    
    private final Logger logger = LogManager.getLogger();
    
    private WebDriver driver;

    @BeforeEach
    public void setUp() { 
        logger.info("setUp");

        ChromeOptions chromeOptions = new ChromeOptions();

        // Read "headless" property set on the command line: 
        //    mvn clean verify -P regression-test-ui -D headless=true
        String headlessSystemProperty = System.getProperty("headless");
        logger.info("headlessSystemProperty: \"" + headlessSystemProperty + "\"");
        if ("true".equals(headlessSystemProperty)) {
            chromeOptions.addArguments("headless");
        }
        
        driver = new ChromeDriver(chromeOptions);

        driver.get(DomainHelper.getBaseUrl() + "/analytics");
    }

    @AfterEach
    public void tearDown() {
        logger.info("tearDown");

        driver.quit();
    }

    @Test
    public void testStoryBookLearningEventsPage() {
        logger.info("testStoryBookLearningEventsPage");

        MainAnalyticsPage mainAnalyticsPage = new MainAnalyticsPage(driver);
        mainAnalyticsPage.pressStoryBookLearningEventsLink();
        
        StoryBookLearningEventsPage storyBookLearningEventsPage = new StoryBookLearningEventsPage(driver);
    }
}
