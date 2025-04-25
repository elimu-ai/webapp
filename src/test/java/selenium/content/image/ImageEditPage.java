package selenium.content.image;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ImageEditPage {
    
    private WebDriver driver;

    public ImageEditPage(WebDriver driver) {
        this.driver = driver;

        driver.findElement(By.id("imageEditPage"));
    }
}
