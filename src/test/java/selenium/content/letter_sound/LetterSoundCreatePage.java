package selenium.content.letter_sound;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LetterSoundCreatePage {
    
    private WebDriver driver;

    public LetterSoundCreatePage(WebDriver driver) {
        this.driver = driver;

        driver.findElement(By.id("letterSoundCreatePage"));
    }
}
