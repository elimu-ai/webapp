package selenium.web.content.letter;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import selenium.ErrorHelper;

public class LetterListPage {

    private WebDriver driver;
    
    @FindBy(className = "letter")
    private List<WebElement> letters;
    
    @FindBy(className = "btn-floating")
    private WebElement addButton;

    public LetterListPage(WebDriver driver) {
        this.driver = driver;
        
        WebDriverWait webDriverWait = new WebDriverWait(driver, 10);
        webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("letterListPage")));
        
        ErrorHelper.verifyNoScriptOrMarkupError(driver);
    }
    
    public int getListCount() {
        return letters.size();
    }
    
    public void clickRandomEditLink() {
        int randomIndex = (int) (Math.random() * letters.size());
        WebElement randomElement = letters.get(randomIndex);
        WebElement editLink = randomElement.findElement(By.className("editLink"));
        editLink.click();
    }
    
    public void clickAddButton() {
        addButton.click();
    }
}
