package org.literacyapp.tasks;

import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.literacyapp.dao.ContributorDao;
import org.literacyapp.model.Contributor;
import org.literacyapp.model.enums.Environment;
import org.literacyapp.model.enums.Team;
import org.literacyapp.util.SlackApiHelper;
import org.literacyapp.web.context.EnvironmentContextLoaderListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class SlackInviteScheduler {
    
    private Logger logger = Logger.getLogger(getClass());

    @Autowired
    private ContributorDao contributorDao;
    
    @Scheduled(cron="00 30 * * * *") // 30 past every hour
    public synchronized void executeInvite() {
        logger.info("executeImport");
        
        if (EnvironmentContextLoaderListener.env != Environment.PROD) {
            return;
        }
        
        List<Contributor> contributors = contributorDao.readAll();
        logger.info("contributors.size(): " + contributors.size());
        for (Contributor contributor : contributors) {
            // If the Contributor has already joined Slack, store the Slack id
            if (StringUtils.isBlank(contributor.getSlackId())) {
                JSONArray slackMembers = SlackApiHelper.getTeamMembers();
                logger.info("slackMembers.length(): " + slackMembers.length());
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
        
        logger.info("executeImport complete");
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
                        SlackApiHelper.inviteToChannel(contributor, team);
                    } else {
                        SlackApiHelper.kickFromChannel(contributor, team);
                    }
                }
            }
        }
        
        logger.info("executeTeamSynchronization complete");
    }
}
