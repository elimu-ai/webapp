package selenium.content.sound;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SoundCreatePage {
    
    private WebDriver driver;

    public SoundCreatePage(WebDriver driver) {
        this.driver = driver;

        driver.findElement(By.id("soundCreatePage"));
    }
}
