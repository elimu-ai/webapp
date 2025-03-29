package selenium.content.letter_sound;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LetterSoundEditPage {
    
    private WebDriver driver;

    public LetterSoundEditPage(WebDriver driver) {
        this.driver = driver;

        driver.findElement(By.id("letterSoundEditPage"));
    }
}
