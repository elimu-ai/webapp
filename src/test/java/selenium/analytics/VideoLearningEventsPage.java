package selenium.analytics;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import selenium.util.ErrorHelper;

public class VideoLearningEventsPage {

    private WebDriver driver;

    public VideoLearningEventsPage(WebDriver driver) {
        this.driver = driver;

        driver.findElement(By.id("videoLearningEventsPage"));
        
        ErrorHelper.verifyNoScriptOrMarkupError(driver);
    }
}
