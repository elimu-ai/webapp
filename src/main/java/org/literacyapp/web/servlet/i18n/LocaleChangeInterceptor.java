package org.literacyapp.web.servlet.i18n;

import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;

public class LocaleChangeInterceptor implements HandlerInterceptor {
	
    private Logger logger = Logger.getLogger(getClass());

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException {
    	logger.info("preHandle");

        Locale locale = RequestContextUtils.getLocale(request);
        logger.info("locale: " + locale);
        
        String serverName = request.getServerName();
        logger.info("serverName: " + serverName);
        if (serverName.startsWith("ar.")) {
            locale = new Locale("ar");
        } else if (serverName.startsWith("en.")) {
            locale = new Locale("en");
        } else if (serverName.startsWith("es.")) {
            locale = new Locale("es");
        } else if (serverName.startsWith("sw.")) {
            locale = new Locale("sw");
        }
        
        request.setAttribute("locale", locale);

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest hsr, HttpServletResponse hsr1, Object o, ModelAndView mav) throws Exception {}

    @Override
    public void afterCompletion(HttpServletRequest hsr, HttpServletResponse hsr1, Object o, Exception excptn) throws Exception {}
}
