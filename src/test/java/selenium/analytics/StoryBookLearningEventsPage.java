package selenium.analytics;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import selenium.util.ErrorHelper;

public class StoryBookLearningEventsPage {

    private WebDriver driver;

    public StoryBookLearningEventsPage(WebDriver driver) {
        this.driver = driver;

        driver.findElement(By.id("storyBookLearningEventsPage"));
        
        ErrorHelper.verifyNoScriptOrMarkupError(driver);
    }
}
