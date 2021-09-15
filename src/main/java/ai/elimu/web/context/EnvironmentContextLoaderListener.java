package ai.elimu.web.context;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.core.io.Resource;
import org.springframework.web.context.ConfigurableWebApplicationContext;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.ServletContextResourceLoader;

import ai.elimu.model.v2.enums.Environment;
import ai.elimu.model.v2.enums.Language;
import java.net.URL;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.config.Configurator;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * Use {@link WebApplicationContextUtils#getWebApplicationContext(javax.servlet.ServletContext)}
 * to access this listener anywhere in the web application, outside of the framework.
 */
public class EnvironmentContextLoaderListener extends ContextLoaderListener {
	
    public static Environment env = Environment.DEV;
	
    public final static Properties PROPERTIES = new Properties();

    private Logger logger = LogManager.getLogger();

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
        
        // Fetch attribute set in the corresponding context file at $JETTY_HOME/webapps/
        String envAttr = (String) servletContext.getAttribute("env");
        if (StringUtils.isNotBlank(envAttr)) {
            env = Environment.valueOf(envAttr);
        }
        logger.info("env: " + env);
        PROPERTIES.put("env", env);
        
        if ((env == Environment.TEST) || (env == Environment.PROD)) {
            // Configure Log4j 2 so that it logs to a file instead of to the console
            // See https://logging.apache.org/log4j/2.x/manual/customconfig.html#Configurator)
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            URL log4j2FileUrl = classLoader.getResource("log4j2_" + env + ".xml"); // E.g. "log4j2_TEST.xml"
            logger.info("log4j2FileUrl: " + log4j2FileUrl);
            String log4j2FilePath = log4j2FileUrl.getFile();
            logger.info("log4j2FilePath: " + log4j2FilePath);
            Configurator.initialize(null, log4j2FilePath);
            logger = LogManager.getLogger();
            logger.info("log4j2FilePath: " + log4j2FilePath);
        }

        super.contextInitialized(event);
    }

    @Override
    protected void customizeContext(ServletContext servletContext, ConfigurableWebApplicationContext applicationContext) {
    	logger.info("customizeContext");
    	
        // Load default settings
        try {
            Resource resourceConfig = new ServletContextResourceLoader(servletContext).getResource("classpath:config.properties");
            PROPERTIES.load(resourceConfig.getInputStream());

            Resource resourceJdbc = new ServletContextResourceLoader(servletContext).getResource("classpath:jdbc.properties");
            PROPERTIES.load(resourceJdbc.getInputStream());

            logger.debug("properties (before overriding): " + PROPERTIES);
        } catch (IOException ex) {
            logger.error(ex);
        }
        
        if ((env == Environment.TEST) || (env == Environment.PROD)) {
            InputStream inputStream = null;
            try {
                // Override config.properties
            	Resource resourceConfig = new ServletContextResourceLoader(servletContext).getResource("classpath:config_" + env + ".properties");
                PROPERTIES.load(resourceConfig.getInputStream());

                // Override jdbc.properties
                Resource resourceJdbc = new ServletContextResourceLoader(servletContext).getResource("classpath:jdbc_" + env + ".properties");
                PROPERTIES.load(resourceJdbc.getInputStream());
                
                String contentLanguage = (String) servletContext.getAttribute("content_language");
                logger.info("contentLanguage: " + contentLanguage);
                PROPERTIES.put("content.language", contentLanguage);
                
                String jdbcUrl = (String) servletContext.getAttribute("jdbc_url");
                PROPERTIES.put("jdbc.url", jdbcUrl);
                
                String jdbcUsername = (String) servletContext.getAttribute("jdbc_username");
                PROPERTIES.put("jdbc.username", jdbcUsername);
                
                String jdbcPasswordAttr = (String) servletContext.getAttribute("jdbc_password");
                PROPERTIES.put("jdbc.password", jdbcPasswordAttr);
                
                String googleApiSecret = (String) servletContext.getAttribute("google_api_secret");
                PROPERTIES.put("google.api.secret", googleApiSecret);
                
                String gitHubApiSecret = (String) servletContext.getAttribute("github_api_secret");
                PROPERTIES.put("github.api.secret", gitHubApiSecret);
                
                String covalentApiKey = (String) servletContext.getAttribute("covalent_api_key");
                PROPERTIES.put("covalent.api.key", covalentApiKey);
                
                if (env == Environment.PROD) {
                    String slackWebhook = (String) servletContext.getAttribute("slack_webhook");
                    PROPERTIES.put("slack.webhook", slackWebhook);
                }
                
                logger.debug("properties (after overriding): " + PROPERTIES);
            } catch (FileNotFoundException ex) {
                logger.error(ex);
            } catch (IOException ex) {
                logger.error(ex);
            } finally {
                try {
                    if (inputStream != null) {
                        inputStream.close();
                    }
                } catch (IOException ex) {
                    logger.error(ex);
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
        
        // Add all supported languages
        PROPERTIES.put("supported.languages", Language.values());

        // Add config properties to application scope
        servletContext.setAttribute("configProperties", PROPERTIES);
        
        servletContext.setAttribute("newLineCharRn", "\r\n");
        servletContext.setAttribute("newLineCharR", "\r");
        servletContext.setAttribute("newLineCharR", "\n");

        super.customizeContext(servletContext, applicationContext);
    }
}
