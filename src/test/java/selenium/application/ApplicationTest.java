package selenium.application;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import lombok.extern.slf4j.Slf4j;
import selenium.util.DomainHelper;

@Slf4j
public class ApplicationTest {
    
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

        driver.get(DomainHelper.getBaseUrl() + "/application/list");
        log.info("driver.getCurrentUrl(): " + driver.getCurrentUrl());
    }

    @AfterEach
    public void tearDown() {
        log.info("tearDown");

        driver.quit();
    }

    @Test
    public void testRandomApplicationEditPage() {
        log.info("testRandomApplicationEditPage");
        
        ApplicationListPage applicationListPage = new ApplicationListPage(driver);
        applicationListPage.pressRandomApplication();
        log.info("driver.getCurrentUrl(): " + driver.getCurrentUrl());

        ApplicationEditPage applicationEditPage = new ApplicationEditPage(driver);
    }

    @Test
    public void testApplicationCreatePage() {
        log.info("testApplicationCreatePage");

        ApplicationListPage applicationListPage = new ApplicationListPage(driver);
        applicationListPage.pressCreateButton();

        ApplicationCreatePage applicationCreatePage = new ApplicationCreatePage(driver);
    }
}
