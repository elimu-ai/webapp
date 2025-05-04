package selenium.application;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ApplicationEditPage {
    
    private WebDriver driver;

    public ApplicationEditPage(WebDriver driver) {
        this.driver = driver;

        driver.findElement(By.id("applicationEditPage"));
    }
}
