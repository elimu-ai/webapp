package ai.elimu.util;

import ai.elimu.model.v2.enums.Environment;
import ai.elimu.web.context.EnvironmentContextLoaderListener;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.IOException;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Helper class for posting notifications in Discord.
 * <p />
 * Documentation: https://discord.com/developers/docs/resources/webhook
 */
public class DiscordHelper {

    private static Logger logger = LogManager.getLogger();
    
    public static void postChatMessage(String content, String embedTitle, String embedDescription, Boolean embedPeerReviewApproved) {
        logger.info("postChatMessage");
        
        if (EnvironmentContextLoaderListener.env == Environment.PROD) {
            JsonObject jsonBody = new JsonObject();
            jsonBody.addProperty("content", content);
            if (StringUtils.isNotBlank(embedTitle)) {
                JsonObject embedsJsonObject = new JsonObject();
                embedsJsonObject.addProperty("title", embedTitle);
                if (StringUtils.isNotBlank(embedDescription)) {
                    embedsJsonObject.addProperty("description", embedDescription);
                }
                if (embedPeerReviewApproved != null) {
                    if (embedPeerReviewApproved) {
                        embedsJsonObject.addProperty("color", 35195); // #00897b
                    } else {
                        embedsJsonObject.addProperty("color", 12000284); // #B71C1C
                    }
                }
                JsonArray embedsJsonArray = new JsonArray();
                embedsJsonArray.add(embedsJsonObject);
                jsonBody.add("embeds", embedsJsonArray);
            }
            logger.info("jsonBody: " + jsonBody);
            CloseableHttpClient client = HttpClients.createDefault();
            String discordWebhookUrl = ConfigHelper.getProperty("discord.webhook.url");
            logger.info("discordWebhookUrl: " + discordWebhookUrl);
            HttpPost httpPost = new HttpPost(discordWebhookUrl);
            try {
                StringEntity entity = new StringEntity(jsonBody.toString());
                httpPost.setEntity(entity);
                httpPost.setHeader("Content-type", "application/json");
                HttpResponse httpResponse = client.execute(httpPost);
                logger.info("httpResponse.getStatusLine(): " + httpResponse.getStatusLine());
                client.close();
            } catch (IOException e) {
               logger.error(e);
            }
        }
    }
}
