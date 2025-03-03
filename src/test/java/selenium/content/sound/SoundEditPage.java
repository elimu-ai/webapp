package selenium.content.sound;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SoundEditPage {
    
    private WebDriver driver;

    public SoundEditPage(WebDriver driver) {
        this.driver = driver;

        driver.findElement(By.id("soundEditPage"));
    }
}
