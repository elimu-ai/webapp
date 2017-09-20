package ai.elimu.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import ai.elimu.model.Contributor;
import ai.elimu.model.enums.Team;

/**
 * http://developer.mailchimp.com/documentation/mailchimp/guides/get-started-with-mailchimp-api-3/
 */
public class MailChimpApiHelper {
    
    // http://developer.mailchimp.com/documentation/mailchimp/
    private static final String BASE_URL = "https://us12.api.mailchimp.com/3.0";
    
    // https://us12.admin.mailchimp.com/account/api/
    private static final String API_KEY = ConfigHelper.getProperty("mailchimp.api.key");
    
    private static final String LIST_ID = "97b79a9d90"; // "elimu.ai"
    
    private static Logger logger = Logger.getLogger(MailChimpApiHelper.class);
    
    /**
     * http://developer.mailchimp.com/documentation/mailchimp/reference/lists/members/#read-get_lists_list_id_members_subscriber_hash
     * 
     * @return {@code null} if a subscription was not found for the given e-mail.
     */
    public static String getMemberInfo(String email) throws IOException {
        logger.info("getMemberInfo");
        
        String memberInfo = null;
         
        try {
            String emailMd5Hash = DigestUtils.md5Hex(email);
            URL url = new URL (BASE_URL + "/lists/" + LIST_ID + "/members/" + emailMd5Hash);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            String encoding = Base64.encodeBase64String(("literacyapp:" + API_KEY).getBytes());
            connection.setRequestProperty("Authorization", "Basic " + encoding);
            
            int responseCode = connection.getResponseCode();
            logger.info("responseCode: " + responseCode);
            InputStream inputStream = null;
            if (responseCode == 200) {
                inputStream = connection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                memberInfo = bufferedReader.readLine();
            }
        } catch (ConnectException ex) {
            // Ignore "Connection timed out"
            logger.warn(null, ex);
        } catch (MalformedURLException ex) {
            logger.error(null, ex);
        }
        
        return memberInfo;
    }
    
    /**
     * Adds new e-mail subscriber.
     * 
     * http://developer.mailchimp.com/documentation/mailchimp/reference/lists/members/#create-post_lists_list_id_members
     */
    public static void subscribeMember(Contributor contributor) {
        logger.info("subscribeMember");
        
        try {
            URL url = new URL (BASE_URL + "/lists/" + LIST_ID + "/members");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            String encoding = Base64.encodeBase64String(("literacyapp:" + API_KEY).getBytes());
            connection.setRequestProperty("Authorization", "Basic " + encoding);
            
            JSONObject messageBody = new JSONObject();
            
            messageBody.put("email_address", contributor.getEmail());
            messageBody.put("status", "subscribed");
            
            JSONObject dataMergeFields = new JSONObject();
            dataMergeFields.put("FNAME", contributor.getFirstName());
            dataMergeFields.put("LNAME", contributor.getLastName());
            if (StringUtils.isNotBlank(contributor.getMotivation())) {
                dataMergeFields.put("MOTIVATION", contributor.getMotivation());
            }
            // TODO: add more fields
            messageBody.put("merge_fields", dataMergeFields);
            
            logger.info("messageBody: " + messageBody);
            
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(messageBody.toString().getBytes());
            outputStream.close();
            
            int responseCode = connection.getResponseCode();
            logger.info("responseCode: " + responseCode);
            InputStream inputStream = null;
            if (responseCode == 200) {
                inputStream = connection.getInputStream();
            } else {
                // Possibly "Member Exists" error (subscribed previously, but unsubscribed or was deleted)
                inputStream = connection.getErrorStream();
                logger.warn("Subscription failed for " + contributor.getEmail());
            }
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String response = bufferedReader.readLine();
            logger.info("response: " + response);
        } catch (ConnectException ex) {
            // Ignore "Connection timed out"
            logger.warn(null, ex);
        } catch (MalformedURLException ex) {
            logger.error(null, ex);
        } catch (IOException ex) {
            logger.error(null, ex);
        }
    }
    
    /**
     * Updates the MailChimp group "Teams".
     * 
     * http://developer.mailchimp.com/documentation/mailchimp/reference/lists/members/#edit-put_lists_list_id_members_subscriber_hash
     */
    public static void updateTeams(String email, Set<Team> teams) {
        logger.info("updateTeams");
        
        try {
            String emailMd5Hash = DigestUtils.md5Hex(email);
            URL url = new URL (BASE_URL + "/lists/" + LIST_ID + "/members/" + emailMd5Hash);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("PUT");
            connection.setDoOutput(true);
            String encoding = Base64.encodeBase64String(("literacyapp:" + API_KEY).getBytes());
            connection.setRequestProperty("Authorization", "Basic " + encoding);
            
            JSONObject messageBody = new JSONObject();
            
            JSONObject dataInterests = new JSONObject();
            
            String groupIdAnalytics = "df9a02f868";
            dataInterests.put(groupIdAnalytics, teams.contains(Team.ANALYTICS));
            
            String groupIdContentCreation = "f49b40a3ae";
            dataInterests.put(groupIdContentCreation, teams.contains(Team.CONTENT_CREATION));
            
            String groupIdDevelopment = "e03b451e05";
            dataInterests.put(groupIdDevelopment, teams.contains(Team.DEVELOPMENT));
            
            String groupIdMarketing = "7d21be999b";
            dataInterests.put(groupIdMarketing, teams.contains(Team.MARKETING));
            
            String groupIdTesting = "f93d076281";
            dataInterests.put(groupIdTesting, teams.contains(Team.TESTING));
            
            String groupIdTranslation = "4bb5825a2f";
            dataInterests.put(groupIdTranslation, teams.contains(Team.TRANSLATION));
            
            String groupIdOther = "a558d106fd";
            dataInterests.put(groupIdOther, teams.contains(Team.OTHER));
            
            messageBody.put("interests", dataInterests);
            
//            logger.info("messageBody: " + messageBody);
            
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(messageBody.toString().getBytes());
            outputStream.close();
            
            int responseCode = connection.getResponseCode();
            logger.info("responseCode: " + responseCode);
            InputStream inputStream = null;
            if (responseCode == 200) {
                inputStream = connection.getInputStream();
            } else {
                inputStream = connection.getErrorStream();
            }
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String response = bufferedReader.readLine();
            logger.info("response: " + response);
        } catch (ConnectException ex) {
            // Ignore "Connection timed out"
            logger.warn(null, ex);
        } catch (MalformedURLException ex) {
            logger.error(null, ex);
        } catch (IOException ex) {
            logger.error(null, ex);
        }
    }
}
