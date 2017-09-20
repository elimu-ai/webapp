package selenium.web;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import selenium.ErrorHelper;

public class WelcomePage {

    private WebDriver driver;

    private WebElement freeTrialLink;

    private WebElement moreInfoButton;

    private WebElement aboutLink;

    public WelcomePage(WebDriver driver) {
        this.driver = driver;
        
        WebDriverWait webDriverWait = new WebDriverWait(driver, 10);
        webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("welcomePage")));
        
        ErrorHelper.verifyNoScriptOrMarkupError(driver);
    }
    
    public void clickMoreInfoButton() {
    	moreInfoButton.click();
    }

    public void clickInformationLinkInFooter() {
        aboutLink.click();
    }

    public void clickFreeTrialLinkInHeader() {
        freeTrialLink.click();
    }
}
