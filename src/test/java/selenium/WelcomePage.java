package selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import selenium.util.ErrorHelper;

public class WelcomePage {

    private WebDriver driver;

    public WelcomePage(WebDriver driver) {
        this.driver = driver;

        driver.findElement(By.id("welcomePage"));
        
        ErrorHelper.verifyNoScriptOrMarkupError(driver);
    }
}
