package selenium;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import static org.junit.Assert.*;

/**
 * @see ErrorHelper
 */
public class MarkupValidationHelper {
    
    private static final String URL = "https://html5.validator.nu/";

    /**
     * Verifies that the HTML is well formed.
     * 
     * @param markup The HTML markup to be tested.
     */
    public static void verifyNoMarkupError(String markup) {
    //        List<MediaType> supportedMediaTypes = new ArrayList<>();
            List<MediaType> supportedMediaTypes = new ArrayList<MediaType>();
            supportedMediaTypes.add(new MediaType("text", "html", Charset.forName("UTF-8")));

            StringHttpMessageConverter stringHttpMessageConverter = new StringHttpMessageConverter();
            stringHttpMessageConverter.setSupportedMediaTypes(supportedMediaTypes);       

    //        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
            List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
            messageConverters.add(stringHttpMessageConverter);

            RestTemplate restTemplate = new RestTemplate();
            restTemplate.setMessageConverters(messageConverters);

            String result = restTemplate.postForObject(URL, markup, String.class);
            System.out.println("result: " + result);

            assertTrue("The document is not valid HTML5: " + markup, result.contains("The document is valid HTML5"));
    }
}
