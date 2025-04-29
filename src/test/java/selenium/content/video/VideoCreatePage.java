package selenium.content.video;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class VideoCreatePage {
    
    private WebDriver driver;

    public VideoCreatePage(WebDriver driver) {
        this.driver = driver;

        driver.findElement(By.id("videoCreatePage"));
    }
}
