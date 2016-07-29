package selenium.web.content.word;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import selenium.ErrorHelper;

public class WordEditPage {

    private WebDriver driver;
    
    private WebElement errorPanel;
    
    private WebElement submitButton;

    public WordEditPage(WebDriver driver) {
        this.driver = driver;
        
        WebDriverWait webDriverWait = new WebDriverWait(driver, 10);
        webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("wordEditPage")));
        
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
