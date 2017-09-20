package selenium;

public class DomainHelper {
    
    public static String getBaseUrl() {
     //return "http://localhost:8080/webapp";
     return "http://test.elimu.ai";
    }
    
    public static String getRestUrlV1() {
        return getBaseUrl() + "/rest/v1";
    }
}
