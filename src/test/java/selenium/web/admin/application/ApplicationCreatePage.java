package selenium.web.admin.application;

import selenium.web.content.allophone.*;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import selenium.ErrorHelper;

public class ApplicationCreatePage {

    private WebDriver driver;
    
    private WebElement errorPanel;
    
    private WebElement submitButton;

    public ApplicationCreatePage(WebDriver driver) {
        this.driver = driver;
        
        WebDriverWait webDriverWait = new WebDriverWait(driver, 10);
        webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("applicationCreatePage")));
        
        ErrorHelper.verifyNoScriptOrMarkupError(driver);
    }
    
    public boolean isErrorMessageDisplayed() {
        try {
            return errorPanel.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }  
    }
    
    public void submitForm() {
        submitButton.click();
    }
}
