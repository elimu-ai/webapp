package selenium.util;

import org.openqa.selenium.WebDriver;

import ai.elimu.entity.enums.Role;

public class SignOnHelper {
    
    public static void signOnRole(WebDriver driver, Role role) {
        driver.get(DomainHelper.getBaseUrl() + "/sign-on/test/role-" + role);
    }
}
