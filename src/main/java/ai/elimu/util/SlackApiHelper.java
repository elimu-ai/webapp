package ai.elimu.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ai.elimu.model.Contributor;
import ai.elimu.model.enums.Team;

public class SlackApiHelper {
    
    private static final String BASE_URL = "https://elimu-ai.slack.com/api";
    
    // https://api.slack.com/docs/oauth-test-tokens
    private static final String API_TOKEN = ConfigHelper.getProperty("slack.api.token");
    
    private static Logger logger = Logger.getLogger(SlackApiHelper.class);
    
    /**
     * If team == null, the id of the #general channel is returned.
     * 
     * See https://api.slack.com/methods/channels.list/test
     */
    public static String getChannelId(Team team) {
        String channelId = "C0LD7203D"; // #general
        
        if (team == Team.ANALYTICS) {
            channelId = "C0LD7JQHZ"; // #team-analytics
        } else if (team == Team.CONTENT_CREATION) {
            channelId = "C0LDBHX3J"; // #team-content-creation
        } else if (team == Team.DEVELOPMENT) {
            channelId = "C0LDBLX3J"; // #team-development
        } else if (team == Team.MARKETING) {
            channelId = "C0LDAQ83Z"; // #team-marketing
        } else if (team == Team.TESTING) {
            channelId = "C0LD4E5LL"; // #team-testing
        } else if (team == Team.TRANSLATION) {
            channelId = "C0LD807B5"; // #team-translation
        }
        
        return channelId;
    }
    
    /**
     * If team == null, the id of the #general channel is used.
     * 
     * Remember to URL encode text before posting.
     * 
     * See https://api.slack.com/methods/chat.postMessage
     */
    public static boolean postMessage(String channelId, String text, String iconUrl, String imageUrl) {
        logger.info("getTeamUsers");
        
        // To prevent exceeded rate limit, wait 1.5 seconds between each API call to the Slack API
        // See https://api.slack.com/docs/rate-limits
        try {
            Thread.sleep(1500);
        } catch (InterruptedException ex) {}
        
        boolean isResponseOk = false;
        
        if (StringUtils.isBlank(channelId)) {
            channelId = getChannelId(null);
        }
        
//        String iconUrlParameter = "";
//        if (StringUtils.isNotBlank(iconUrl)) {
//            iconUrlParameter = "&icon_url=" + iconUrl;
//        } else {
//            iconUrlParameter = "&icon_emoji=%3Aauni%3A";
//        }
        // Fall-back to emoji to avoid "404 File Not Found" when avatar cannot be reached
        String iconUrlParameter = "&icon_emoji=%3Aauni%3A";

        String imageUrlParameter = "";
        if (StringUtils.isNotBlank(imageUrl)) {
            JSONArray attachments = new JSONArray();
            JSONObject attachment = new JSONObject();
            attachment.put("text", imageUrl);
            attachment.put("image_url", imageUrl);
            attachments.put(attachment);
            imageUrlParameter = "&attachments=" + attachments;
        }     
        
        String response = null;
        
        try {
            URL url = new URL (BASE_URL + "/chat.postMessage?token=" + API_TOKEN + "&as_user=false" + "&username=elimu.ai" + "&unfurl_links=false" + "&channel=" + channelId + "&text=" + text + iconUrlParameter + imageUrlParameter);
            logger.info("url: " + url);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            
            int responseCode = connection.getResponseCode();
            logger.info("responseCode: " + responseCode);
            InputStream inputStream = null;
            if (responseCode == 200) {
                inputStream = connection.getInputStream();
            } else {
                inputStream = connection.getErrorStream();
            }
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            response = bufferedReader.readLine();
            logger.info("response: " + response);
            JSONObject jSONObject = new JSONObject(response);
            isResponseOk = jSONObject.getBoolean("ok");
        } catch (MalformedURLException ex) {
            logger.error(null, ex);
        } catch (IOException ex) {
            logger.error(null, ex);
        } catch (JSONException ex) {
            logger.error("response: " + response, ex);
        }
        
        return isResponseOk;
    }
    
    /**
     * https://api.slack.com/methods/users.list
     */
    public static JSONArray getUserList() {
        logger.info("getUserList");
        
        // To prevent exceeded rate limit, wait 1.5 seconds between each API call to the Slack API
        // See https://api.slack.com/docs/rate-limits
        try {
            Thread.sleep(1500);
        } catch (InterruptedException ex) {}
        
        JSONArray members = null;
        
        String response = null;
        
        try {
            URL url = new URL (BASE_URL + "/users.list?token=" + API_TOKEN);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            
            int responseCode = connection.getResponseCode();
            logger.info("responseCode: " + responseCode);
            InputStream inputStream = null;
            if (responseCode == 200) {
                inputStream = connection.getInputStream();
            } else {
                inputStream = connection.getErrorStream();
            }
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            response = bufferedReader.readLine();
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.has("members")) {
                members = jsonObject.getJSONArray("members");
            }
        } catch (MalformedURLException ex) {
            logger.error(null, ex);
        } catch (IOException ex) {
            logger.error(null, ex);
        }
        
        return members;
    }
    
    /**
     * https://levels.io/slack-typeform-auto-invite-sign-ups/
     */
    public static boolean sendMemberInvite(Contributor contributor) {
        logger.info("sendMemberInvite");
        
        // To prevent exceeded rate limit, wait 1.5 seconds between each API call to the Slack API
        // See https://api.slack.com/docs/rate-limits
        try {
            Thread.sleep(1500);
        } catch (InterruptedException ex) {}
        
        boolean isResponseOk = false;
        
        String response = null;
        
        String firstNameParam = "";
        if (StringUtils.isNotBlank(contributor.getFirstName())) {
            firstNameParam += "&first_name=" + URLEncoder.encode(contributor.getFirstName());
        }
        
        String lastNameParam = "";
        if (StringUtils.isNotBlank(contributor.getLastName())) {
            lastNameParam += "&last_name=" + URLEncoder.encode(contributor.getLastName());
        }

        try {
            URL url = new URL (BASE_URL + "/users.admin.invite?token=" + API_TOKEN + "&email=" + contributor.getEmail() + firstNameParam + lastNameParam);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            
            int responseCode = connection.getResponseCode();
            logger.info("responseCode: " + responseCode);
            InputStream inputStream = null;
            if (responseCode == 200) {
                inputStream = connection.getInputStream();
            } else {
                inputStream = connection.getErrorStream();
            }
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            response = bufferedReader.readLine();
            logger.info("response: " + response);
            JSONObject jSONObject = new JSONObject(response);
            isResponseOk = jSONObject.getBoolean("ok");
        } catch (MalformedURLException ex) {
            logger.error(null, ex);
        } catch (IOException ex) {
            logger.error(null, ex);
        } catch (JSONException ex) {
            logger.error("response: " + response, ex);
        }
        
        return isResponseOk;
    }
    
    /**
     * https://api.slack.com/methods/channels.invite
     * 
     * @return {@code true} if Contributor was successfully added, {@code false} otherwise.
     */
    public static boolean inviteToChannel(Contributor contributor, Team team) {
        logger.info("inviteToChannel");
        
        // To prevent exceeded rate limit, wait 1.5 seconds between each API call to the Slack API
        // See https://api.slack.com/docs/rate-limits
        try {
            Thread.sleep(1500);
        } catch (InterruptedException ex) {}
        
        boolean isResponseOk = false;
        
        String channelId = getChannelId(team);
        
        String response = null;
        
        try {
            URL url = new URL (BASE_URL + "/channels.invite?token=" + API_TOKEN + "&channel=" + channelId + "&user=" + contributor.getSlackId());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            
            int responseCode = connection.getResponseCode();
            logger.info("responseCode: " + responseCode);
            InputStream inputStream = null;
            if (responseCode == 200) {
                inputStream = connection.getInputStream();
            } else {
                inputStream = connection.getErrorStream();
            }
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            response = bufferedReader.readLine();
            logger.info("response: " + response);
            JSONObject jSONObject = new JSONObject(response);
            isResponseOk = jSONObject.getBoolean("ok");
            if (isResponseOk) {
                logger.info("Invited " + contributor.getEmail() + " to channel " + channelId);
            }
        } catch (MalformedURLException ex) {
            logger.error(null, ex);
        } catch (IOException ex) {
            logger.error(null, ex);
        } catch (JSONException ex) {
            logger.error("response: " + response, ex);
        }
        
        return isResponseOk;
    }
    
    /**
     * https://api.slack.com/methods/channels.kick
     * 
     * @return {@code true} if Contributor was successfully removed, {@code false} otherwise.
     */
    public static boolean kickFromChannel(Contributor contributor, Team team) {
        logger.info("kickFromChannel");
        
        // To prevent exceeded rate limit, wait 1.5 seconds between each API call to the Slack API
        // See https://api.slack.com/docs/rate-limits
        try {
            Thread.sleep(1500);
        } catch (InterruptedException ex) {}
        
        boolean isResponseOk = false;
        
        String channelId = getChannelId(team);
        
        String response = null;
        
        try {
            URL url = new URL (BASE_URL + "/channels.kick?token=" + API_TOKEN + "&channel=" + channelId + "&user=" + contributor.getSlackId());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            
            int responseCode = connection.getResponseCode();
            logger.info("responseCode: " + responseCode);
            InputStream inputStream = null;
            if (responseCode == 200) {
                inputStream = connection.getInputStream();
            } else {
                inputStream = connection.getErrorStream();
            }
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            response = bufferedReader.readLine();
            logger.info("response: " + response);
            JSONObject jSONObject = new JSONObject(response);
            isResponseOk = jSONObject.getBoolean("ok");
            if (isResponseOk) {
                logger.info("Removed " + contributor.getEmail() + " from channel " + channelId);
            }
        } catch (MalformedURLException ex) {
            logger.error(null, ex);
        } catch (IOException ex) {
            logger.error(null, ex);
        } catch (JSONException ex) {
            logger.error("response: " + response, ex);
        }
        
        return isResponseOk;
    }
}
