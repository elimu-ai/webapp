package selenium.content.letter_sound;

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
public class LetterSoundTest {
    
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
    public void testRandomLetterSoundEditPage() {
        log.info("testRandomLetterSoundEditPage");
        
        MainContentPage mainContentPage = new MainContentPage(driver);
        mainContentPage.pressLetterSoundListLink();

        LetterSoundListPage letterSoundListPage = new LetterSoundListPage(driver);
        letterSoundListPage.pressRandomLetterSound();
        log.info("driver.getCurrentUrl(): " + driver.getCurrentUrl());

        LetterSoundEditPage letterSoundEditPage = new LetterSoundEditPage(driver);
    }

    @Test
    public void testLetterSoundCreatePage() {
        log.info("testLetterSoundCreatePage");

        MainContentPage mainContentPage = new MainContentPage(driver);
        mainContentPage.pressLetterSoundListLink();

        LetterSoundListPage letterSoundListPage = new LetterSoundListPage(driver);
        letterSoundListPage.pressCreateButton();

        LetterSoundCreatePage letterSoundCreatePage = new LetterSoundCreatePage(driver);
    }
}
