package selenium;

import ai.elimu.entity.enums.Role;
import org.openqa.selenium.WebDriver;

public class SignOnHelper {
    
    public static void signOnRole(WebDriver driver, Role role) {
        driver.get(DomainHelper.getBaseUrl() + "/sign-on/test/role-" + role);
    }
}
