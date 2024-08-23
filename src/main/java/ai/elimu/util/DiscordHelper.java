package ai.elimu.util;

import ai.elimu.model.v2.enums.Environment;
import ai.elimu.web.context.EnvironmentContextLoaderListener;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.commons.lang.StringUtils;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpResponse;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * Helper class for posting notifications in Discord.
 * <p />
 * Documentation: https://discord.com/developers/docs/resources/webhook
 */
public class DiscordHelper {

    private static Logger logger = LogManager.getLogger();

    public static void sendChannelMessage(
            String content,
            String embedTitle,
            String embedDescription,
            Boolean embedPeerReviewApproved,
            String embedThumbnailUrl
    ) {
        logger.info("sendChannelMessage");

        // Prepare the JSON body
        JsonObject jsonBody = new JsonObject();
        jsonBody.addProperty("content", content);
        if (StringUtils.isNotBlank(embedTitle)) {
            // See https://discord.com/developers/docs/resources/channel#embed-object
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
            if (StringUtils.isNotBlank(embedThumbnailUrl)) {
                JsonObject thumbnailJsonObject = new JsonObject();
                thumbnailJsonObject.addProperty("url", embedThumbnailUrl);
                embedsJsonObject.add("thumbnail", thumbnailJsonObject);
            }
            JsonArray embedsJsonArray = new JsonArray();
            embedsJsonArray.add(embedsJsonObject);
            jsonBody.add("embeds", embedsJsonArray);
        }
        logger.info("jsonBody: " + jsonBody);

        if (EnvironmentContextLoaderListener.env == Environment.PROD) {
            // Send the message to Discord
            CloseableHttpClient client = HttpClients.createDefault();
            String discordWebhookUrl = ConfigHelper.getProperty("discord.webhook.url");
            logger.info("discordWebhookUrl: " + discordWebhookUrl);
            HttpPost httpPost = new HttpPost(discordWebhookUrl);
            try {
                StringEntity entity = new StringEntity(jsonBody.toString());
                httpPost.setEntity(entity);
                httpPost.setHeader("Content-type", "application/json");
                HttpResponse httpResponse = client.execute(httpPost);
                logger.info("httpResponse.getStatusLine(): " + httpResponse);
                client.close();
            } catch (IOException e) {
               logger.error(e);
            }
        }
    }
}
