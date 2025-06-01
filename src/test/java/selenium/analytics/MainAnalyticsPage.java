package selenium.analytics;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import selenium.util.ErrorHelper;

public class MainAnalyticsPage {

    private WebDriver driver;

    public MainAnalyticsPage(WebDriver driver) {
        this.driver = driver;

        driver.findElement(By.id("mainAnalyticsPage"));
        
        ErrorHelper.verifyNoScriptOrMarkupError(driver);
    }

    public void pressVideoLearningEventsLink() {
        WebElement link = driver.findElement(By.id("videoLearningEventsLink"));
        link.click();
    }
}
