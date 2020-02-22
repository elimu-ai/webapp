package selenium.web;

import org.openqa.selenium.WebDriver;

import selenium.ErrorHelper;

public class WelcomePage {

    private WebDriver driver;

    public WelcomePage(WebDriver driver) {
        this.driver = driver;
        
//        WebDriverWait webDriverWait = new WebDriverWait(driver, 10);
//        webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("welcomePage")));
        
        ErrorHelper.verifyNoScriptOrMarkupError(driver);
    }
}
