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

    public void pressLettersLink() {
        WebElement link = driver.findElement(By.id("letterListLink"));
        link.click();
    }
}
