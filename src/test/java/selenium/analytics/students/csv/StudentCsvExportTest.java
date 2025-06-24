package selenium.analytics.students.csv;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import kong.unirest.core.HttpResponse;
import kong.unirest.core.Unirest;
import lombok.extern.slf4j.Slf4j;
import selenium.analytics.students.StudentListPage;
import selenium.analytics.students.StudentPage;
import selenium.util.DomainHelper;

@Slf4j
public class StudentCsvExportTest {

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

        driver.get(DomainHelper.getBaseUrl() + "/analytics/students");
        log.info("driver.getCurrentUrl(): " + driver.getCurrentUrl());
    }

    @AfterEach
    public void tearDown() {
        log.info("tearDown");

        driver.quit();
    }

    @Test
    public void testExportCsv_LetterSoundAssessmentEvents() {
        log.info("testExportCsv_LetterSoundAssessmentEvents");
        
        StudentListPage studentListPage = new StudentListPage(driver);
        studentListPage.pressRandomStudent();
        log.info("driver.getCurrentUrl(): " + driver.getCurrentUrl());

        StudentPage studentPage = new StudentPage(driver);
        
        String fileUrl = studentPage.getLetterSoundAssessmentEventsUrl();
        log.info("fileUrl: " + fileUrl);
        HttpResponse<String> getResponse = Unirest.get(fileUrl).asString();
        log.info("getResponse.getStatus(): " + getResponse.getStatus());
        log.info("getResponse.isSuccess(): " + getResponse.isSuccess());
        assertTrue(getResponse.isSuccess());
    }

    @Test
    public void testExportCsv_LetterSoundLearningEvents() {
        log.info("testExportCsv_LetterSoundLearningEvents");
        
        StudentListPage studentListPage = new StudentListPage(driver);
        studentListPage.pressRandomStudent();
        log.info("driver.getCurrentUrl(): " + driver.getCurrentUrl());

        StudentPage studentPage = new StudentPage(driver);
        
        String fileUrl = studentPage.getLetterSoundLearningEventsUrl();
        log.info("fileUrl: " + fileUrl);
        HttpResponse<String> getResponse = Unirest.get(fileUrl).asString();
        log.info("getResponse.getStatus(): " + getResponse.getStatus());
        log.info("getResponse.isSuccess(): " + getResponse.isSuccess());
        assertTrue(getResponse.isSuccess());
    }

    @Test
    public void testExportCsv_WordLearningEvents() {
        log.info("testExportCsv_WordLearningEvents");
        
        StudentListPage studentListPage = new StudentListPage(driver);
        studentListPage.pressRandomStudent();
        log.info("driver.getCurrentUrl(): " + driver.getCurrentUrl());

        StudentPage studentPage = new StudentPage(driver);
        
        String fileUrl = studentPage.getWordLearningEventsUrl();
        log.info("fileUrl: " + fileUrl);
        HttpResponse<String> getResponse = Unirest.get(fileUrl).asString();
        log.info("getResponse.getStatus(): " + getResponse.getStatus());
        log.info("getResponse.isSuccess(): " + getResponse.isSuccess());
        assertTrue(getResponse.isSuccess());
    }
}
