package selenium.contributor;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import selenium.util.ErrorHelper;

public class ContributorPage {

    private WebDriver driver;

    public ContributorPage(WebDriver driver) {
        this.driver = driver;

        driver.findElement(By.id("contributorPage"));
        
        ErrorHelper.verifyNoScriptOrMarkupError(driver);
    }
}
