package selenium.web.content.allophone;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import selenium.ErrorHelper;

public class AllophoneListPage {

    private WebDriver driver;
    
    @FindBy(className = "btn-floating")
    private WebElement addButton;

    public AllophoneListPage(WebDriver driver) {
        this.driver = driver;
        
        WebDriverWait webDriverWait = new WebDriverWait(driver, 10);
        webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("allophoneListPage")));
        
        ErrorHelper.verifyNoScriptOrMarkupError(driver);
    }
    
    public void clickAddButton() {
        addButton.click();
    }
}
