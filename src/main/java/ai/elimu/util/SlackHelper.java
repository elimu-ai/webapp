package ai.elimu.util;

import ai.elimu.model.enums.Environment;
import ai.elimu.web.context.EnvironmentContextLoaderListener;
import com.google.gson.JsonObject;
import java.io.IOException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Helper class for posting notifications in Slack.
 */
public class SlackHelper {
    
    private static Logger logger = LogManager.getLogger();
    
    public static void postChatMessage(String text) {
        logger.info("postChatMessage");
        
        if (EnvironmentContextLoaderListener.env == Environment.PROD) {
            JsonObject jsonBody = new JsonObject();
            jsonBody.addProperty("text", text);
            logger.info("jsonBody: " + jsonBody);
            CloseableHttpClient client = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost("https://hooks.slack.com/services/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");
            try {
                StringEntity entity = new StringEntity(jsonBody.toString());
                httpPost.setEntity(entity);
                httpPost.setHeader("Content-type", "application/json");
                client.execute(httpPost);
                client.close();
            } catch (IOException e) {
               logger.error(e);
            }
        }
    }
}
