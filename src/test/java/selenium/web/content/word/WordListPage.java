package selenium.web.content.word;

import selenium.web.content.word.*;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import selenium.ErrorHelper;

public class WordListPage {

    private WebDriver driver;
    
    @FindBy(className = "word")
    private List<WebElement> words;
    
    @FindBy(className = "btn-floating")
    private WebElement addButton;

    public WordListPage(WebDriver driver) {
        this.driver = driver;
        
        WebDriverWait webDriverWait = new WebDriverWait(driver, 10);
        webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("wordListPage")));
        
        ErrorHelper.verifyNoScriptOrMarkupError(driver);
    }
    
    public int getListCount() {
        return words.size();
    }
    
    public void clickRandomEditLink() {
        int randomIndex = (int) (Math.random() * words.size());
        WebElement randomElement = words.get(randomIndex);
        WebElement editLink = randomElement.findElement(By.className("editLink"));
        editLink.click();
    }
    
    public void clickAddButton() {
        addButton.click();
    }
}
