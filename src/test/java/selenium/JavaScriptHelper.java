package selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import static org.junit.Assert.*;

/**
 * This class uses information stored in {@code /WEB-INF/jsp/error/javascript-error.jsp}
 * 
 * @see ErrorHelper
 */
public class JavaScriptHelper {

    /**
     * Note that this only works with Firefox.
     */
    public static void verifyNoJavaScriptError(WebDriver driver) {
        WebElement body = driver.findElement(By.tagName("body"));
        String jsError = body.getAttribute("data-js-error");
        assertNull("JavaScript error: '" + jsError + "'", jsError);
    }
}
