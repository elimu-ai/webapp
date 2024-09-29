package selenium.contributor;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import selenium.util.ErrorHelper;

public class ContributorListPage {

    private WebDriver driver;

    public ContributorListPage(WebDriver driver) {
        this.driver = driver;

        driver.findElement(By.id("contributorListPage"));
        
        ErrorHelper.verifyNoScriptOrMarkupError(driver);
    }
}
