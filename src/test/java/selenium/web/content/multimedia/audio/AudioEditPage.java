package selenium.web.content.multimedia.audio;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import selenium.ErrorHelper;

public class AudioEditPage {

    private WebDriver driver;
    
    private WebElement errorPanel;
    
    private WebElement submitButton;

    public AudioEditPage(WebDriver driver) {
        this.driver = driver;
        
        WebDriverWait webDriverWait = new WebDriverWait(driver, 10);
        webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("audioEditPage")));
        
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
    
    public void addWordLabel(String word) {
        int numberOfWordLabelsBefore = driver.findElements(By.cssSelector("#wordLabelContainer > .chip")).size();
        
        Select select = new Select(driver.findElement(By.id("wordId")));
        select.selectByVisibleText(word);
        
        // Wait for the Ajax call to complete
        WebDriverWait webDriverWait = new WebDriverWait(driver, 10);
        webDriverWait.until(ExpectedConditions.numberOfElementsToBe(By.cssSelector("#wordLabelContainer > .chip"), numberOfWordLabelsBefore + 1));
    }
    
    public void removeWordLabel(String word) {
        int numberOfWordLabelsBefore = driver.findElements(By.cssSelector("#wordLabelContainer > .chip")).size();
        
        WebElement wordLabel = driver.findElement(By.cssSelector("[data-wordvalue=\"" + word + "\"]"));
        WebElement wordDeleteLink = wordLabel.findElement(By.className("wordDeleteLink"));
//        wordDeleteLink.click();
        // Hack to enable Firefox to click the delete icon
        wordDeleteLink.findElement(By.className("material-icons")).click();
        
        // Wait for the Ajax call to complete
        WebDriverWait webDriverWait = new WebDriverWait(driver, 10);
        webDriverWait.until(ExpectedConditions.numberOfElementsToBe(By.cssSelector("#wordLabelContainer > .chip"), numberOfWordLabelsBefore - 1));
    }
}
