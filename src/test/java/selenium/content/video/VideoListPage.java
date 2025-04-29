package selenium.content.video;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class VideoListPage {
    
    private WebDriver driver;

    public VideoListPage(WebDriver driver) {
        this.driver = driver;

        driver.findElement(By.id("videoListPage"));
    }

    public void pressRandomVideo() {
        List<WebElement> links = driver.findElements(By.className("editLink"));
        int randomIndex = (int) (Math.random() * links.size());
        WebElement randomLink = links.get(randomIndex);
        randomLink.click();
    }

    public void pressCreateButton() {
        WebElement button = driver.findElement(By.id("createButton"));
        button.click();
    }
}
