package ai.elimu.util;

import ai.elimu.model.v2.enums.Environment;
import ai.elimu.model.v2.enums.Language;
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
            String discordWebhookUrl = null;
            Language language = Language.valueOf(ConfigHelper.getProperty("content.language"));
            if (language == Language.BEN) {
                discordWebhookUrl = "https://discord.com/api/webhooks/931147996744278097/5yzw51lR3O6eRXDSuIelHI__dIohCJiUrxHT9vXmKa84E9D9siHdkiLqj7idIUUV54PX";
            } else if (language == Language.ENG) {
                discordWebhookUrl = "https://discord.com/api/webhooks/931230205228507226/lfoNh8qtkZe-_GpKkG4DoXzNuMaw2iackwY-oTdLDZvbtxUB0vjbNW0lYkAp4EcBIGd6";
            } else if (language == Language.FIL) {
                discordWebhookUrl = "https://discord.com/api/webhooks/930524667318509689/P8aziLBP8Km39zRIjIwex8rijislt5WpHVobPEiwIycBfB1DnSyOpF256LfMMX8opBF4";
            } else if (language == Language.HIN) {
                discordWebhookUrl = "https://discord.com/api/webhooks/930524975985721354/J5MS8KOXQulp-9CI-ZBuaGE0E8ofco5hpJTkET60KUyetOdIoGWabmxjROId2q2N7w2D";
            } else if (language == Language.SWA) {
                discordWebhookUrl = "https://discord.com/api/webhooks/930525214838767646/KxI5EKOSULsbY4ul6P-iYMsE85IOC1l6Oom85OdVj8ISvrgS2XoNUj5BzgBACYI4pOpC";
            } else if (language == Language.URD) {
                discordWebhookUrl = "https://discord.com/api/webhooks/930530996858814535/cftYuEZALEjptOipddGerHKj387lYtRRVRjFsdOAmDxQqjCt8ov8ptu0caLju4f7PRSX";
            } else if (language == Language.XHO) {
                discordWebhookUrl = "https://discord.com/api/webhooks/930531245891416146/LBE43uve0RkKQkPMc7JG1cBnvEaTGZ8Cnkvb0LPrY7J99Q0BfnpA4LbwBi8yhUXczzt-";
            }
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
