package org.literacyapp.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.literacyapp.model.Contributor;
import org.literacyapp.model.enums.Team;

public class SlackApiHelper {
    
    private static final String BASE_URL = "https://literacyapp.slack.com/api";
    
    // https://api.slack.com/docs/oauth-test-tokens
    private static final String API_TOKEN = ConfigHelper.getProperty("slack.api.token");
    
    private static Logger logger = Logger.getLogger(SlackApiHelper.class);
    
    /**
     * If team == null, the id of the #general channel is returned.
     * 
     * See https://api.slack.com/methods/channels.list/test
     */
    private static String getChannelId(Team team) {
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
    public static boolean postMessage(Team team, String text, String iconUrl) {
        logger.info("getTeamUsers");
        
        boolean isResponseOk = false;
        
        String channelId = getChannelId(team);
        
        String iconUrlParameter = "";
        if (StringUtils.isNotBlank(iconUrl)) {
            iconUrlParameter = "&icon_url=" + iconUrl;
        }
        
        String response = null;
        
        try {
            URL url = new URL (BASE_URL + "/chat.postMessage?token=" + API_TOKEN + "&channel=" + channelId + "&text=" + text + iconUrlParameter);
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
    public static JSONArray getTeamMembers() {
        logger.info("getTeamUsers");
        
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
            members = jsonObject.getJSONArray("members");
        } catch (MalformedURLException ex) {
            logger.error(null, ex);
        } catch (IOException ex) {
            logger.error(null, ex);
        } catch (JSONException ex) {
            logger.error("response: " + response, ex);
        }
        
        return members;
    }
    
    /**
     * https://levels.io/slack-typeform-auto-invite-sign-ups/
     */
    public static boolean sendMemberInvite(Contributor contributor) {
        logger.info("sendMemberInvite");
        
        boolean isResponseOk = false;
        
        String response = null;
        
        try {
            URL url = new URL (BASE_URL + "/users.admin.invite?token=" + API_TOKEN + "&email=" + contributor.getEmail());
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
