package ai.elimu.web.servlet;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

public class CustomSimpleMappingExceptionResolver extends SimpleMappingExceptionResolver {
    
    private final Logger logger = Logger.getLogger(getClass());

    @Override
    protected void logException(Exception ex, HttpServletRequest request) {
        logger.error(buildLogMessage(ex, request), ex);
    }
    
    
}
