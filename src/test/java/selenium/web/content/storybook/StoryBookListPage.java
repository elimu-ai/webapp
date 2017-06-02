package selenium.web.content.storybook;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import selenium.ErrorHelper;

public class StoryBookListPage {

    private WebDriver driver;
    
    @FindBy(className = "storyBook")
    private List<WebElement> storyBooks;
    
    @FindBy(className = "btn-floating")
    private WebElement addButton;

    public StoryBookListPage(WebDriver driver) {
        this.driver = driver;
        
        WebDriverWait webDriverWait = new WebDriverWait(driver, 10);
        webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("storyBookListPage")));
        
        ErrorHelper.verifyNoScriptOrMarkupError(driver);
    }
    
    public int getListCount() {
        return storyBooks.size();
    }
    
    public void clickRandomEditLink() {
        int randomIndex = (int) (Math.random() * storyBooks.size());
        WebElement randomElement = storyBooks.get(randomIndex);
        WebElement editLink = randomElement.findElement(By.className("editLink"));
        editLink.click();
    }
    
    public void clickAddButton() {
        addButton.click();
    }
}
