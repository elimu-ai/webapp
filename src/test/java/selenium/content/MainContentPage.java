package selenium.content;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class MainContentPage {
    
    private WebDriver driver;

    public MainContentPage(WebDriver driver) {
        this.driver = driver;

        driver.findElement(By.id("mainContentPage"));
    }

    public void pressLetterListLink() {
        WebElement link = driver.findElement(By.id("letterListLink"));
        link.click();
    }

    public void pressSoundListLink() {
        WebElement link = driver.findElement(By.id("soundListLink"));
        link.click();
    }

    public void pressLetterSoundListLink() {
        WebElement link = driver.findElement(By.id("letterSoundListLink"));
        link.click();
    }

    public void pressNumberListLink() {
        WebElement link = driver.findElement(By.id("numberListLink"));
        link.click();
    }

    public void pressWordListLink() {
        WebElement link = driver.findElement(By.id("wordListLink"));
        link.click();
    }

    public void pressEmojiListLink() {
        WebElement link = driver.findElement(By.id("emojiListLink"));
        link.click();
    }

    public void pressImageListLink() {
        WebElement link = driver.findElement(By.id("imageListLink"));
        link.click();
    }
}
