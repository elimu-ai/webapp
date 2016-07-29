package selenium.web.content.number;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import selenium.ErrorHelper;

public class NumberListPage {

    private WebDriver driver;
    
    @FindBy(className = "number")
    private List<WebElement> numbers;
    
    @FindBy(className = "btn-floating")
    private WebElement addButton;

    public NumberListPage(WebDriver driver) {
        this.driver = driver;
        
        WebDriverWait webDriverWait = new WebDriverWait(driver, 10);
        webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("numberListPage")));
        
        ErrorHelper.verifyNoScriptOrMarkupError(driver);
    }
    
    public int getListCount() {
        return numbers.size();
    }
    
    public void clickRandomEditLink() {
        int randomIndex = (int) (Math.random() * numbers.size());
        WebElement randomElement = numbers.get(randomIndex);
        WebElement editLink = randomElement.findElement(By.className("editLink"));
        editLink.click();
    }
    
    public void clickAddButton() {
        addButton.click();
    }
}
