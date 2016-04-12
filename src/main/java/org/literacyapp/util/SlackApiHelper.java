package org.literacyapp.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.literacyapp.model.Contributor;
import org.literacyapp.model.enums.Team;

public class SlackApiHelper {
    
    private static final String BASE_URL = "https://literacyapp.slack.com/api";
    
    private static Logger logger = Logger.getLogger(SlackApiHelper.class);
    
    /**
     * https://api.slack.com/methods/users.list
     */
    public static JSONArray getTeamMembers() {
        logger.info("getTeamUsers");
        
        // https://api.slack.com/docs/oauth-test-tokens
        String API_TOKEN = ConfigHelper.getProperty("slack.api.token");
        
        JSONArray members = null;
        
        try {
            URL url = new URL (BASE_URL + "/users.list?token=" + API_TOKEN);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            
            InputStream inputStream = (InputStream) connection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader (new InputStreamReader(inputStream));
            String response = bufferedReader.readLine();
            JSONObject jsonObject = new JSONObject(response);
            members = jsonObject.getJSONArray("members");
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
        
        // https://api.slack.com/docs/oauth-test-tokens
        String API_TOKEN = ConfigHelper.getProperty("slack.api.token");
        
        boolean isResponseOk = false;
        
        try {
            URL url = new URL (BASE_URL + "/users.admin.invite?token=" + API_TOKEN + "&email=" + contributor.getEmail());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            
            InputStream inputStream = (InputStream) connection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader (new InputStreamReader(inputStream));
            String response = bufferedReader.readLine();
            logger.info("response: " + response);
            JSONObject jSONObject = new JSONObject(response);
            isResponseOk = jSONObject.getBoolean("ok");
        } catch (MalformedURLException ex) {
            logger.error(null, ex);
        } catch (IOException ex) {
            logger.error(null, ex);
        }
        
        return isResponseOk;
    }
    
    public static boolean inviteToChannel(Contributor contributor, Team team) {
        logger.info("inviteToChannel");
        
        // https://api.slack.com/docs/oauth-test-tokens
        String API_TOKEN = ConfigHelper.getProperty("slack.api.token");
        
        boolean isResponseOk = false;
        
        String channelId = null;
        if (team == Team.ANALYTICS) {
            channelId = "C0LD7JQHZ";
        } else if (team == Team.CONTENT_CREATION) {
            channelId = "C0LDBHX3J";
        } else if (team == Team.DEVELOPMENT) {
            channelId = "C0LDBLX3J";
        } else if (team == Team.MARKETING) {
            channelId = "C0LDAQ83Z";
        } else if (team == Team.TESTING) {
            channelId = "C0LD4E5LL";
        } else if (team == Team.TRANSLATION) {
            channelId = "C0LD807B5";
        }
        
        try {
            URL url = new URL (BASE_URL + "/channels.invite?token=" + API_TOKEN + "&channel=" + channelId + "&user=" + contributor.getSlackId());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            
            InputStream inputStream = (InputStream) connection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader (new InputStreamReader(inputStream));
            String response = bufferedReader.readLine();
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
        }
        
        return isResponseOk;
    }
    
    public static boolean kickFromChannel(Contributor contributor, Team team) {
        logger.info("kickFromChannel");
        
        // https://api.slack.com/docs/oauth-test-tokens
        String API_TOKEN = ConfigHelper.getProperty("slack.api.token");
        
        boolean isResponseOk = false;
        
        String channelId = null;
        if (team == Team.ANALYTICS) {
            channelId = "C0LD7JQHZ";
        } else if (team == Team.CONTENT_CREATION) {
            channelId = "C0LDBHX3J";
        } else if (team == Team.DEVELOPMENT) {
            channelId = "C0LDBLX3J";
        } else if (team == Team.MARKETING) {
            channelId = "C0LDAQ83Z";
        } else if (team == Team.TESTING) {
            channelId = "C0LD4E5LL";
        } else if (team == Team.TRANSLATION) {
            channelId = "C0LD807B5";
        }
        
        try {
            URL url = new URL (BASE_URL + "/channels.kick?token=" + API_TOKEN + "&channel=" + channelId + "&user=" + contributor.getSlackId());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            
            InputStream inputStream = (InputStream) connection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader (new InputStreamReader(inputStream));
            String response = bufferedReader.readLine();
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
        }
        
        return isResponseOk;
    }
}
