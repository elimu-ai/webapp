package ai.elimu.tasks;

import java.io.IOException;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import ai.elimu.dao.ContributorDao;
import ai.elimu.model.Contributor;
import ai.elimu.model.enums.Environment;
import ai.elimu.util.MailChimpApiHelper;
import ai.elimu.web.context.EnvironmentContextLoaderListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class MailChimpScheduler {
    
    private Logger logger = Logger.getLogger(getClass());

    @Autowired
    private ContributorDao contributorDao;
    
    @Scheduled(cron="00 10 * * * *") // 10 past every hour
    public synchronized void executeImport() {
        logger.info("executeImport");
        
        if (EnvironmentContextLoaderListener.env != Environment.PROD) {
            return;
        }
        
        List<Contributor> contributors = contributorDao.readAll();
        logger.info("contributors.size(): " + contributors.size());
        for (Contributor contributor : contributors) {
            try {
                // Check if contributor is already subscribed to the mailing list
                String memberInfo = MailChimpApiHelper.getMemberInfo(contributor.getEmail());
                if (StringUtils.isBlank(memberInfo)) {
                    MailChimpApiHelper.subscribeMember(contributor);
                }
            } catch (IOException ex) {
                logger.error(null, ex);
                break;
            }
        }
        
        logger.info("executeImport complete");
    }
    
    /**
     * Sync data registered for existing subscribers.
     */
    @Scheduled(cron="00 20 * * * *") // 20 past every hour
    public synchronized void executeDataSync() {
        logger.info("executeDataSync");
        
        if (EnvironmentContextLoaderListener.env != Environment.PROD) {
            return;
        }
        
        List<Contributor> contributors = contributorDao.readAll();
        logger.info("contributors.size(): " + contributors.size());
        for (Contributor contributor : contributors) {
            try {
                String memberInfo = MailChimpApiHelper.getMemberInfo(contributor.getEmail());
                if (StringUtils.isNotBlank(memberInfo)) {
                    // Sync Contributor data with mailing list
                    MailChimpApiHelper.updateTeams(contributor.getEmail(), contributor.getTeams());
                }
            } catch (IOException ex) {
                logger.error(null, ex);
                break;
            }
        }
        
        logger.info("executeDataSync complete");
    }
}
