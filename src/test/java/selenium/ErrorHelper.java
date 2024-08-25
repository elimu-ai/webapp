package selenium;

import org.openqa.selenium.WebDriver;

public class ErrorHelper {
    
    public static void verifyNoScriptOrMarkupError(WebDriver driver) {
        JavaScriptHelper.verifyNoJavaScriptError(driver);
//        MarkupValidationHelper.verifyNoMarkupError(driver.getPageSource());
    }
}
