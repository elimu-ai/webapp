package ai.elimu.tasks;

import java.util.List;
import java.util.Locale;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import ai.elimu.dao.ContributorDao;
import ai.elimu.model.Contributor;
import ai.elimu.model.enums.Environment;
import ai.elimu.model.enums.Team;
import ai.elimu.util.Mailer;
import ai.elimu.util.SlackApiHelper;
import ai.elimu.web.context.EnvironmentContextLoaderListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class SlackInviteScheduler {
    
    private Logger logger = Logger.getLogger(getClass());

    @Autowired
    private ContributorDao contributorDao;
    
    @Autowired
    private MessageSource messageSource;
    
    @Scheduled(cron="00 30 * * * *") // 30 past every hour
    public synchronized void executeInvite() {
        logger.info("executeInvite");
        
        if (EnvironmentContextLoaderListener.env != Environment.PROD) {
            return;
        }
        
        JSONArray slackMembers = SlackApiHelper.getUserList();
        if (slackMembers != null) {
            logger.info("slackMembers.length(): " + slackMembers.length());

            List<Contributor> contributors = contributorDao.readAll();
            logger.info("contributors.size(): " + contributors.size());
            for (Contributor contributor : contributors) {
                // If the Contributor has already joined Slack, store the Slack id
                if (StringUtils.isBlank(contributor.getSlackId())) {
                    for (int i = 0; i < slackMembers.length(); i++) {
                        JSONObject member = slackMembers.getJSONObject(i);
                        String slackId = member.getString("id");
                        JSONObject memberProfile = member.getJSONObject("profile");
                        if (!memberProfile.isNull("email")) {
                            String email = memberProfile.getString("email");
                            if (contributor.getEmail().equals(email)) {
                                contributor.setSlackId(slackId);
                                contributorDao.update(contributor);
                            }
                        }
                    }
                }

                // If the Contributor has not already joined Slack, send an invite
                if (StringUtils.isBlank(contributor.getSlackId())) {
                    SlackApiHelper.sendMemberInvite(contributor);
                }
            }
        }
        
        logger.info("executeInvite complete");
    }
    
    /**
     * Add to (and remove from) Slack channels based on Contributor's teams 
     * registered on web site.
     */
    @Scheduled(cron="00 40 * * * *") // 40 past every hour
    public synchronized void executeTeamSynchronization() {
        logger.info("executeTeamSynchronization");
        
        if (EnvironmentContextLoaderListener.env != Environment.PROD) {
            return;
        }
        
        List<Contributor> contributors = contributorDao.readAll();
        logger.info("contributors.size(): " + contributors.size());
        for (Contributor contributor : contributors) {
            if (contributor.getTeams() == null) {
                continue;
            }
            
            if (StringUtils.isNotBlank(contributor.getSlackId())) {
                for (Team team : Team.values()) {
                    if (team == Team.OTHER) {
                        continue;
                    }
                    
                    if (contributor.getTeams().contains(team)) {
                        // To prevent exceeded rate limit, wait 1 second between each API call to the Slack API
                        // See https://api.slack.com/docs/rate-limits
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException ex) {}

                        boolean isResponseOk = SlackApiHelper.inviteToChannel(contributor, team);
                        if (isResponseOk) {
                            // Send welcome e-mail specific to the team
                            String to = contributor.getEmail();
                            String from = "elimu.ai <info@elimu.ai>";
                            Locale locale = new Locale("en");
                            String teamName = messageSource.getMessage("team." + team, null, locale);
                            String subject = "You joined the " + teamName + " team";
                            String title = "Team: " + teamName;
                            String firstName = StringUtils.isBlank(contributor.getFirstName()) ? "" : contributor.getFirstName();
                            String htmlText = "<p>Hi, " + firstName + "</p>";
                            htmlText += "<p>You were added to the \"" + teamName + "\" team. The responsibility of this team is the following:</p>";
                            String teamMissionStatement = messageSource.getMessage("team." + team + ".mission.statement", null, locale);
                            htmlText += "<p><blockquote>\"" + teamMissionStatement + "\"</blockquote></p>";
                            
                            // Team-specific content
                            if (team == Team.DEVELOPMENT) {
                                htmlText += "<h2>GitHub</h2>";
                                htmlText += "<p>The source code of the project is located at https://github.com/elimu-ai</p>";
                                htmlText += "<p>Please send us your GitHub username so that we can add you as a GitHub team member. Either send your username by replying to this e-mail or posting it at the Slack channel (see below).</p>";
                                htmlText += "<p>If you don't yet have a GitHub user, you can create one here: https://github.com/join</p>";
                                htmlText += "<p>Are you new to GitHub? See https://guides.github.com/introduction/flow</p>";
                            }
                            
                            htmlText += "<h2>Slack channel</h2>";
                            htmlText += "<p>We just added you to the Slack channel <a href=\"https://elimu-ai.slack.com/messages/" + SlackApiHelper.getChannelId(team) + "\">#" + SlackApiHelper.getChannelId(team) + "</a></p>";
                            htmlText += "<p>To chat with the other team members, click the button below:</p>";
                            String buttonText = "Go to Slack channel";
                            String buttonUrl = "https://elimu-ai.slack.com/messages/" + SlackApiHelper.getChannelId(team);
                            Mailer.sendHtmlWithButton(to, null, from, subject, title, htmlText, buttonText, buttonUrl);
                        }
                    } /*else {
                        SlackApiHelper.kickFromChannel(contributor, team);
                    }*/
                }
            }
        }
        
        logger.info("executeTeamSynchronization complete");
    }
}