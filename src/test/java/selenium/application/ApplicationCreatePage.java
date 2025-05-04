package selenium.application;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ApplicationCreatePage {
    
    private WebDriver driver;

    public ApplicationCreatePage(WebDriver driver) {
        this.driver = driver;

        driver.findElement(By.id("applicationCreatePage"));
    }
}
