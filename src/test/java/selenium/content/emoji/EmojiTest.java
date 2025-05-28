package selenium.content.emoji;

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
public class EmojiTest {
    
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
    public void testRandomEmojiEditPage() {
        log.info("testRandomEmojiEditPage");
        
        MainContentPage mainContentPage = new MainContentPage(driver);
        mainContentPage.pressEmojiListLink();

        EmojiListPage emojiListPage = new EmojiListPage(driver);
        emojiListPage.pressRandomEmoji();
        log.info("driver.getCurrentUrl(): " + driver.getCurrentUrl());

        EmojiEditPage emojiEditPage = new EmojiEditPage(driver);
    }

    @Test
    public void testEmojiCreatePage() {
        log.info("testEmojiCreatePage");

        MainContentPage mainContentPage = new MainContentPage(driver);
        mainContentPage.pressEmojiListLink();

        EmojiListPage emojiListPage = new EmojiListPage(driver);
        emojiListPage.pressCreateButton();

        EmojiCreatePage emojiCreatePage = new EmojiCreatePage(driver);
    }
}
