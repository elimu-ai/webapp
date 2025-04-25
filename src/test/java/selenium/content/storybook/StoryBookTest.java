package selenium.content.storybook;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import lombok.extern.slf4j.Slf4j;
import selenium.content.MainContentPage;
import selenium.util.DomainHelper;

@Slf4j
public class StoryBookTest {
    
    private WebDriver driver;

    @BeforeEach
    public void setUp() { 
        log.info("setUp");

        ChromeOptions chromeOptions = new ChromeOptions();

        // Read "headless" property set on the command line: 
        //    mvn clean verify -P regression-test-ui -D headless=true
        String headlessSystemProperty = System.getProperty("headless");
        log.info("headlessSystemProperty: \"" + headlessSystemProperty + "\"");
        if ("true".equals(headlessSystemProperty)) {
            chromeOptions.addArguments("headless");
        }
        
        driver = new ChromeDriver(chromeOptions);

        driver.get(DomainHelper.getBaseUrl() + "/content");
    }

    @AfterEach
    public void tearDown() {
        log.info("tearDown");

        driver.quit();
    }

    @Test
    public void testRandomStoryBookEditPage() {
        log.info("testRandomStoryBookEditPage");
        
        MainContentPage mainContentPage = new MainContentPage(driver);
        mainContentPage.pressStoryBookListLink();

        StoryBookListPage storyBookListPage = new StoryBookListPage(driver);
        storyBookListPage.pressRandomStoryBook();
        log.info("driver.getCurrentUrl(): " + driver.getCurrentUrl());

        StoryBookEditPage storyBookEditPage = new StoryBookEditPage(driver);
    }

    @Test
    public void testStoryBookCreatePage() {
        log.info("testStoryBookCreatePage");

        MainContentPage mainContentPage = new MainContentPage(driver);
        mainContentPage.pressStoryBookListLink();

        StoryBookListPage storyBookListPage = new StoryBookListPage(driver);
        storyBookListPage.pressCreateButton();

        StoryBookCreatePage storyBookCreatePage = new StoryBookCreatePage(driver);
    }
}
