package ai.elimu.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/**
 * Stores information about where website visitors are coming from.
 */
public class CookieHelper {
	
    private static Logger logger = Logger.getLogger(CookieHelper.class);

    public static String getReferrer(HttpServletRequest request) {
        String referrer = null;
        for (Cookie cookie : request.getCookies()) {
            if ("referrerElimuAi".equals(cookie.getName())) {
                referrer = cookie.getValue();
                logger.info("referrer: " + referrer);
            }
        }
        return referrer;
    }

    public static void storeRefererCookie(HttpServletResponse response, String referrer)  {	
        if (referrer.length() > 1000) {
            referrer = referrer.substring(0, 1000);
        }
        logger.info("Storing cookie 'referrerElimuAi': " + referrer);
        Cookie cookie = new Cookie("referrerElimuAi", referrer);
        cookie.setDomain(".elimu.ai");
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24 * 30 * 8); // 6 months
        response.addCookie(cookie);
    }


    public static String getUtmSource(HttpServletRequest request) {
        String utmSource = null;
        for (Cookie cookie : request.getCookies()) {
            if ("utmSourceElimuAi".equals(cookie.getName())) {
                utmSource = cookie.getValue();
                logger.info("utmSource: " + utmSource);
            }
        }
        return utmSource;
    }

    public static void storeSourceCookie(HttpServletResponse response, String value)  {
        logger.info("Storing cookie 'utmSourceElimuAi': " + value);
        Cookie cookie = new Cookie("utmSourceElimuAi", value);
        cookie.setDomain(".elimu.ai");
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24 * 30 * 8); // 6 months
        response.addCookie(cookie);
    }


    public static String getUtmMedium(HttpServletRequest request) {
        String utmMedium = null;
        for (Cookie cookie : request.getCookies()) {
            if ("utmMediumElimuAi".equals(cookie.getName())) {
                utmMedium = cookie.getValue();
                logger.info("utmMedium: " + utmMedium);
            }
        }
        return utmMedium;
    }

    public static void storeMediumCookie(HttpServletResponse response, String value)  {
        logger.info("Storing cookie 'utmMediumElimuAi': " + value);
        Cookie cookie = new Cookie("utmMediumElimuAi", value);
        cookie.setDomain(".elimu.ai");
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24 * 30 * 8); // 6 months
        response.addCookie(cookie);
    }


    public static String getUtmCampaign(HttpServletRequest request) {
        String utmCampaign = null;
        for (Cookie cookie : request.getCookies()) {
            if ("utmCampaignElimuAi".equals(cookie.getName())) {
                utmCampaign = cookie.getValue();
                logger.info("utmCampaign: " + utmCampaign);
            }
        }
        return utmCampaign;
    }

    public static void storeCampaignCookie(HttpServletResponse response, String value)  {
        logger.info("Storing cookie 'utmCampaignElimuAi': " + value);
        Cookie cookie = new Cookie("utmCampaignElimuAi", value);
        cookie.setDomain(".elimu.ai");
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24 * 30 * 8); // 6 months
        response.addCookie(cookie);
    }

    public static String getUtmTerm(HttpServletRequest request) {
        String utmTerm = null;
        for (Cookie cookie : request.getCookies()) {
            if ("utmTermElimuAi".equals(cookie.getName())) {
                utmTerm = cookie.getValue();
                logger.info("utmTerm: " + utmTerm);
            }
        }
        return utmTerm;
    }

    public static void storeTermCookie(HttpServletResponse response, String value)  {
        logger.info("Storing cookie 'utmTermElimuAi': " + value);
        Cookie cookie = new Cookie("utmTermElimuAi", value);
        cookie.setDomain(".elimu.ai");
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24 * 30 * 8); // 6 months
        response.addCookie(cookie);
    }

    public static Long getReferralId(HttpServletRequest request) {
        Long referralId = null;
        for (Cookie cookie : request.getCookies()) {
            if ("referralIdElimuAi".equals(cookie.getName())) {
                referralId = Long.valueOf(cookie.getValue());
                logger.info("referralId: " + referralId);
            }
        }
        return referralId;
    }

    public static void storeIdCookie(HttpServletResponse response, String value)  {
        logger.info("Storing cookie 'referralIdElimuAi': " + value);
        Cookie cookie = new Cookie("referralIdElimuAi", value);
        cookie.setDomain(".elimu.ai");
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24 * 30 * 8); // 6 months
        response.addCookie(cookie);
    }
}
