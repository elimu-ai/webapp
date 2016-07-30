package selenium.web.content.multimedia.video;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import selenium.ErrorHelper;

public class VideoCreatePage {

    private WebDriver driver;
    
    private WebElement errorPanel;
    
    private WebElement submitButton;

    public VideoCreatePage(WebDriver driver) {
        this.driver = driver;
        
        WebDriverWait webDriverWait = new WebDriverWait(driver, 10);
        webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("videoCreatePage")));
        
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
