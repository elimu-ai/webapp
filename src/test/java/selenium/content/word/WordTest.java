package selenium.content.word;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import lombok.extern.slf4j.Slf4j;
import selenium.content.MainContentPage;
import selenium.content.storybook.StoryBookEditPage;
import selenium.content.storybook.StoryBookListPage;
import selenium.util.DomainHelper;

@Slf4j
public class WordTest {
    
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
    public void testRandomWordEditPage() {
        log.info("testRandomWordEditPage");
        
        MainContentPage mainContentPage = new MainContentPage(driver);
        mainContentPage.pressWordListLink();

        WordListPage wordListPage = new WordListPage(driver);
        wordListPage.pressRandomWord();
        log.info("driver.getCurrentUrl(): " + driver.getCurrentUrl());

        WordEditPage wordEditPage = new WordEditPage(driver);
    }

    @Test
    public void testWordCreatePage() {
        log.info("testWordCreatePage");

        MainContentPage mainContentPage = new MainContentPage(driver);
        mainContentPage.pressWordListLink();

        WordListPage wordListPage = new WordListPage(driver);
        wordListPage.pressCreateButton();

        WordCreatePage wordCreatePage = new WordCreatePage(driver);
    }

    @Test
    public void testWordAutoFill() {
        log.info("testWordAutoFill");

        MainContentPage mainContentPage = new MainContentPage(driver);
        mainContentPage.pressStoryBookListLink();

        StoryBookListPage storyBookListPage = new StoryBookListPage(driver);
        storyBookListPage.pressRandomStoryBook();
        log.info("driver.getCurrentUrl(): " + driver.getCurrentUrl());

        StoryBookEditPage storyBookEditPage = new StoryBookEditPage(driver);
        storyBookEditPage.pressRandomAutoFillWordLink();
        log.info("driver.getCurrentUrl(): " + driver.getCurrentUrl());

        WordCreatePage wordCreatePage = new WordCreatePage(driver);
        // TODO
    }
}
