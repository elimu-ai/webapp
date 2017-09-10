package ai.elimu.web.context;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.core.io.Resource;
import org.springframework.web.context.ConfigurableWebApplicationContext;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.ServletContextResourceLoader;

import ai.elimu.model.enums.Environment;

/**
 * Use {@code WebApplicationContextUtils.getWebApplicationContext(servletContext)}
 * to access this listener anywhere in the web application, outside of the framework.
 */
public class EnvironmentContextLoaderListener extends ContextLoaderListener {
	
    public static Environment env = Environment.DEV;
	
    public final static Properties PROPERTIES = new Properties();

    private Logger logger = Logger.getLogger(getClass());

    /**
     * Note that environment-specific config.properties and
     * jdbc.properties files only require properties with _different_ values than
     * the default one. The file log4j.properties, however, requires <b>all</b>
     * properties to be present!
     */
    @Override
    public void contextInitialized(ServletContextEvent event) {
    	logger.info("contextInitialized");
    	
        ServletContext servletContext = event.getServletContext();
        
        // Fetch attribute set in the corresponding context file at jetty.home/contexts/
        String envAttr = (String) servletContext.getAttribute("env");
        if (StringUtils.isNotBlank(envAttr)) {
            env = Environment.valueOf(envAttr);
        }
        logger.info("env: " + env);
        PROPERTIES.put("env", env);

        if ((env == Environment.TEST) || (env == Environment.PROD)) {
            // Clear existing Log4j configuration
            LogManager.resetConfiguration();
            
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            URL environmentSpecificLog4JFile = classLoader.getResource(env + "_log4j.properties");
            new PropertyConfigurator().doConfigure(environmentSpecificLog4JFile, LogManager.getLoggerRepository());
            logger = Logger.getLogger(getClass());
            logger.info("environmentSpecificLog4JFile: " + environmentSpecificLog4JFile);
        }

        super.contextInitialized(event);
    }

    @Override
    protected void customizeContext(ServletContext servletContext, ConfigurableWebApplicationContext applicationContext) {
    	logger.info("contextInitialized");
    	
        // Load default settings
        try {
            Resource resourceConfig = new ServletContextResourceLoader(servletContext).getResource("classpath:config.properties");
            PROPERTIES.load(resourceConfig.getInputStream());

            Resource resourceJdbc = new ServletContextResourceLoader(servletContext).getResource("classpath:jdbc.properties");
            PROPERTIES.load(resourceJdbc.getInputStream());

            logger.debug("properties (before overriding): " + PROPERTIES);
        } catch (IOException ex) {
            logger.error(null, ex);
        }
        
        if ((env == Environment.TEST) || (env == Environment.PROD)) {
        	InputStream inputStream = null;
            try {
                // Override config.properties
            	Resource resourceConfig = new ServletContextResourceLoader(servletContext).getResource("classpath:" + env + "_config.properties");
                PROPERTIES.load(resourceConfig.getInputStream());

                // Override jdbc.properties
                Resource resourceJdbc = new ServletContextResourceLoader(servletContext).getResource("classpath:" + env + "_jdbc.properties");
                PROPERTIES.load(resourceJdbc.getInputStream());
                
                String jdbcPasswordAttr = (String) servletContext.getAttribute("jdbc_password");
                PROPERTIES.put("jdbc.password", jdbcPasswordAttr);
                
                String googleApiSecret = (String) servletContext.getAttribute("google_api_secret");
                PROPERTIES.put("google.api.secret", googleApiSecret);
                
                String facebookApiSecret = (String) servletContext.getAttribute("facebook_api_secret");
                PROPERTIES.put("facebook.api.secret", facebookApiSecret);
                
                String gitHubApiSecret = (String) servletContext.getAttribute("github_api_secret");
                PROPERTIES.put("github.api.secret", gitHubApiSecret);
                
                if (env == Environment.PROD) {
                    String mailChimpApiKey = (String) servletContext.getAttribute("mailchimp_api_key");
                    PROPERTIES.put("mailchimp.api.key", mailChimpApiKey);
                    
                    String slackApiToken = (String) servletContext.getAttribute("slack_api_token");
                    PROPERTIES.put("slack.api.token", slackApiToken);
                }
                
                String appstoreSecret = (String) servletContext.getAttribute("appstore_secret");
                PROPERTIES.put("appstore.secret", appstoreSecret);
                
                logger.debug("properties (after overriding): " + PROPERTIES);
            } catch (FileNotFoundException ex) {
                logger.error(null, ex);
            } catch (IOException ex) {
                logger.error(null, ex);
            } finally {
                try {
                    if (inputStream != null) {
                        inputStream.close();
                    }
                } catch (IOException ex) {
                    logger.error(null, ex);
                }
            }

            PropertyPlaceholderConfigurer propertyPlaceholderConfigurer = new PropertyPlaceholderConfigurer();
            propertyPlaceholderConfigurer.setProperties(PROPERTIES);
            applicationContext.addBeanFactoryPostProcessor(new BeanFactoryPostProcessor() {
                @Override
                public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
                    PropertyPlaceholderConfigurer propertyPlaceholderConfigurer = new PropertyPlaceholderConfigurer();
                    propertyPlaceholderConfigurer.setProperties(PROPERTIES);
                    propertyPlaceholderConfigurer.postProcessBeanFactory(configurableListableBeanFactory);
                }
            });
        }

        // Add config properties to application scope
        servletContext.setAttribute("configProperties", PROPERTIES);

        super.customizeContext(servletContext, applicationContext);
    }
}
