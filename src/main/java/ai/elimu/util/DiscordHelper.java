package ai.elimu.util;

import ai.elimu.model.v2.enums.Environment;
import ai.elimu.web.context.EnvironmentContextLoaderListener;
import lombok.extern.slf4j.Slf4j;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.apache.commons.lang.StringUtils;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpResponse;
import org.apache.hc.core5.http.io.entity.StringEntity;
import java.io.IOException;

/**
 * Helper class for posting notifications in Discord.
 * <p />
 * Documentation: https://discord.com/developers/docs/resources/webhook
 */
@Slf4j
public class DiscordHelper {

    public static void sendChannelMessage(
            String content,
            String embedTitle,
            String embedDescription,
            Boolean embedPeerReviewApproved,
            String embedThumbnailUrl
    ) {
        log.info("sendChannelMessage");

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
        log.info("jsonBody: " + jsonBody);

        if (EnvironmentContextLoaderListener.env == Environment.PROD) {
            // Send the message to Discord
            CloseableHttpClient client = HttpClients.createDefault();
            String discordWebhookUrl = ConfigHelper.getProperty("discord.webhook.url");
            log.info("discordWebhookUrl: " + discordWebhookUrl);
            HttpPost httpPost = new HttpPost(discordWebhookUrl);
            
            StringEntity entity = new StringEntity(jsonBody.toString());
            httpPost.setEntity(entity);
            httpPost.setHeader("Content-type", "application/json");
            log.info("httpPost: " + httpPost);
            new Thread(() -> {
                try {
                    HttpResponse httpResponse = client.execute(httpPost);
                    log.info("httpResponse.getStatusLine(): " + httpResponse);
                    log.info("httpResponse: " + httpResponse);
                    client.close();
                } catch (IOException e) {
                    log.error(e.getMessage());
                }
            }).start();
        }
    }
}
