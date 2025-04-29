package selenium.content.video;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class VideoEditPage {
    
    private WebDriver driver;

    public VideoEditPage(WebDriver driver) {
        this.driver = driver;

        driver.findElement(By.id("videoEditPage"));
    }
}
