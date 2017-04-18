package selenium.web.content.multimedia.video;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import selenium.ErrorHelper;

public class VideoListPage {

    private WebDriver driver;
    
    @FindBy(className = "video")
    private List<WebElement> videos;
    
    @FindBy(className = "btn-floating")
    private WebElement addButton;

    public VideoListPage(WebDriver driver) {
        this.driver = driver;
        
        WebDriverWait webDriverWait = new WebDriverWait(driver, 10);
        webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("videoListPage")));
        
        ErrorHelper.verifyNoScriptOrMarkupError(driver);
    }
    
    public int getListCount() {
        return videos.size();
    }
    
    public void clickRandomEditLink() {
        int randomIndex = (int) (Math.random() * videos.size());
        WebElement randomElement = videos.get(randomIndex);
        WebElement editLink = randomElement.findElement(By.className("editLink"));
        editLink.click();
    }
    
    public void clickAddButton() {
        addButton.click();
    }
}
