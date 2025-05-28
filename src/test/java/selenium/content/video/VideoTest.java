package selenium.content.video;

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
public class VideoTest {
    
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
        log.info("driver.getCurrentUrl(): " + driver.getCurrentUrl());
    }

    @AfterEach
    public void tearDown() {
        log.info("tearDown");

        driver.quit();
    }

    @Test
    public void testRandomVideoEditPage() {
        log.info("testRandomVideoEditPage");
        
        MainContentPage mainContentPage = new MainContentPage(driver);
        mainContentPage.pressVideoListLink();

        VideoListPage videoListPage = new VideoListPage(driver);
        videoListPage.pressRandomVideo();
        log.info("driver.getCurrentUrl(): " + driver.getCurrentUrl());

        VideoEditPage videoEditPage = new VideoEditPage(driver);
    }

    @Test
    public void testVideoCreatePage() {
        log.info("testVideoCreatePage");

        MainContentPage mainContentPage = new MainContentPage(driver);
        mainContentPage.pressVideoListLink();

        VideoListPage videoListPage = new VideoListPage(driver);
        videoListPage.pressCreateButton();

        VideoCreatePage videoCreatePage = new VideoCreatePage(driver);
    }
}
