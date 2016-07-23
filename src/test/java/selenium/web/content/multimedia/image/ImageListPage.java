package selenium.web.content.multimedia.image;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import selenium.ErrorHelper;

public class ImageListPage {

    private WebDriver driver;
    
    @FindBy(className = "image")
    private List<WebElement> images;
    
    @FindBy(className = "btn-floating")
    private WebElement addButton;

    public ImageListPage(WebDriver driver) {
        this.driver = driver;
        
        WebDriverWait webDriverWait = new WebDriverWait(driver, 10);
        webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("imageListPage")));
        
        ErrorHelper.verifyNoScriptOrMarkupError(driver);
    }
    
    public int getListCount() {
        return images.size();
    }
    
    public void clickRandomEditLink() {
        int randomIndex = (int) (Math.random() * images.size());
        WebElement randomElement = images.get(randomIndex);
        WebElement editLink = randomElement.findElement(By.className("editLink"));
        editLink.click();
    }
    
    public void clickAddButton() {
        addButton.click();
    }
}
