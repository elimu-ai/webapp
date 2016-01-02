package selenium.error_pages;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

public class ErrorPage {
    
    private WebElement errorPage;
    
    public boolean isDisplayed() {
        try {
            return errorPage.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }
    
    public boolean isCode403() {
        return errorPage.getAttribute("class").contains("403");
    }
    
    public boolean isCode404() {
        return errorPage.getAttribute("class").contains("404");
    }
}
