package selenium;

public class DomainHelper {
    
    public static String getBaseUrl() {
//        return "http://localhost:8080/literacyapp-webapp";
        return "http://test.literacyapp.org";
    }
    
    public static String getRestUrl() {
        return getBaseUrl() + "/rest";
    }
}
