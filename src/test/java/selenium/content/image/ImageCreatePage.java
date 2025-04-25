package selenium.content.image;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ImageCreatePage {
    
    private WebDriver driver;

    public ImageCreatePage(WebDriver driver) {
        this.driver = driver;

        driver.findElement(By.id("imageCreatePage"));
    }
}
