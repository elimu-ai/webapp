package selenium.web.content.multimedia.audio;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import selenium.ErrorHelper;

public class AudioListPage {

    private WebDriver driver;
    
    @FindBy(className = "audio")
    private List<WebElement> audios;
    
    @FindBy(className = "btn-floating")
    private WebElement addButton;

    public AudioListPage(WebDriver driver) {
        this.driver = driver;
        
        WebDriverWait webDriverWait = new WebDriverWait(driver, 10);
        webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("audioListPage")));
        
        ErrorHelper.verifyNoScriptOrMarkupError(driver);
    }
    
    public int getListCount() {
        return audios.size();
    }
    
    public void clickRandomEditLink() {
        int randomIndex = (int) (Math.random() * audios.size());
        WebElement randomElement = audios.get(randomIndex);
        WebElement editLink = randomElement.findElement(By.className("editLink"));
        editLink.click();
    }
    
    public void clickAddButton() {
        addButton.click();
    }
}
