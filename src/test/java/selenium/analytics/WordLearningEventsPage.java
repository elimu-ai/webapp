package selenium.analytics;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import selenium.util.ErrorHelper;

public class WordLearningEventsPage {

    private WebDriver driver;

    public WordLearningEventsPage(WebDriver driver) {
        this.driver = driver;

        driver.findElement(By.id("wordLearningEventsPage"));
        
        ErrorHelper.verifyNoScriptOrMarkupError(driver);
    }
}
