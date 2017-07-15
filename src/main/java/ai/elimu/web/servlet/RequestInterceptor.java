package ai.elimu.web.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import ai.elimu.util.CookieHelper;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class RequestInterceptor implements HandlerInterceptor {
	
    private Logger logger = Logger.getLogger(getClass());

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException {
    	logger.debug("preHandle");
        
        String referrer = request.getHeader("referer");
        logger.debug("referrer: " + referrer);
        if (StringUtils.isNotBlank(referrer) && !referrer.contains(request.getServerName())) {
            String refererCookie = CookieHelper.getReferrer(request);
            boolean refererCookieExists = StringUtils.isNotBlank(refererCookie);
            if (!refererCookieExists) {
                CookieHelper.storeRefererCookie(response, referrer);
            }
        }
    	
        String utmSource = request.getParameter("utm_source");
        logger.debug("utmSource: " + utmSource);
        if (StringUtils.isNotBlank(utmSource)) {
            String cookie = CookieHelper.getUtmSource(request);
            boolean campaignCookieExists = StringUtils.isNotBlank(cookie);
            if (!campaignCookieExists) {
                CookieHelper.storeSourceCookie(response, utmSource);
            }
        }

        String utmMedium = request.getParameter("utm_medium");
        logger.debug("utmMedium: " + utmMedium);
        if (StringUtils.isNotBlank(utmMedium)) {
            String cookie = CookieHelper.getUtmMedium(request);
            boolean cookieExists = StringUtils.isNotBlank(cookie);
            if (!cookieExists) {
                CookieHelper.storeMediumCookie(response, utmMedium);
            }
        }
        
    	String utmCampaign = request.getParameter("utm_campaign");
        logger.debug("utmCampaign: " + utmCampaign);
        if (StringUtils.isNotBlank(utmCampaign)) {
            String cookie = CookieHelper.getUtmCampaign(request);
            boolean cookieExists = StringUtils.isNotBlank(cookie);
            if (!cookieExists) {
                CookieHelper.storeCampaignCookie(response, utmCampaign);
            }
        }
        
        String utmTerm = request.getParameter("utm_term");
        logger.debug("utmTerm: " + utmTerm);
        if (StringUtils.isNotBlank(utmTerm)) {
            String cookie = CookieHelper.getUtmTerm(request);
            boolean cookieExists = StringUtils.isNotBlank(cookie);
            if (!cookieExists) {
                CookieHelper.storeTermCookie(response, utmTerm);
            }
        }
        
        String referralId = request.getParameter("id");
        logger.debug("referralId: " + referralId);
        if (StringUtils.isNotBlank(referralId)) {
            Long cookie = CookieHelper.getReferralId(request);
            if (cookie == null) {
                try {
                    Long.parseLong(referralId);
                    CookieHelper.storeIdCookie(response, referralId);
                } catch (NumberFormatException e) {
                    logger.warn("referralId: " + referralId, e);
                }
            }
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest hsr, HttpServletResponse hsr1, Object o, ModelAndView mav) throws Exception {}

    @Override
    public void afterCompletion(HttpServletRequest hsr, HttpServletResponse hsr1, Object o, Exception excptn) throws Exception {}
}
