package selenium.web.content.contributor;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import selenium.ErrorHelper;

public class EditTeamsPage {

    private WebDriver driver;
    
    private WebElement errorPanel;

    @FindBy(name = "teams")
    private List<WebElement> teams;
    
    private WebElement submitButton;

    public EditTeamsPage(WebDriver driver) {
        this.driver = driver;
        
        WebDriverWait webDriverWait = new WebDriverWait(driver, 10);
        webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("editTeamsPage")));
        
        ErrorHelper.verifyNoScriptOrMarkupError(driver);
    }
    
    public boolean isErrorMessageDisplayed() {
        try {
            return errorPanel.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }  
    }
    
    public boolean isListEmpty() {
        return teams.isEmpty();
    }
    
    public void clearSelection() {
        for (WebElement team : teams) {
            if (team.isSelected()) {
                String teamName = team.getAttribute("id");
                WebElement label = driver.findElement(By.cssSelector("[for=" + teamName + "]"));
                label.click();
            }
        }
    }
    
    public void submitForm() {
        submitButton.click();
    }
}
