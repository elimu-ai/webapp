package selenium.content.letter;

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
public class LetterTest {
    
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
    public void testRandomLetterEditPage() {
        log.info("testRandomLetterEditPage");
        
        MainContentPage mainContentPage = new MainContentPage(driver);
        mainContentPage.pressLetterListLink();

        LetterListPage letterListPage = new LetterListPage(driver);
        letterListPage.pressRandomLetter();

        LetterEditPage letterEditPage = new LetterEditPage(driver);
    }

    @Test
    public void testLetterCreatPage() {
        log.info("testLetterCreatePage");

        MainContentPage mainContentPage = new MainContentPage(driver);
        mainContentPage.pressLetterListLink();

        LetterListPage letterListPage = new LetterListPage(driver);
        letterListPage.pressCreateButton();

        LetterCreatePage letterCreatePage = new LetterCreatePage(driver);
    }
}
