package ai.elimu.web.servlet;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;

import org.apache.logging.log4j.Logger;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

public class CustomSimpleMappingExceptionResolver extends SimpleMappingExceptionResolver {
    
    private final Logger logger = LogManager.getLogger();

    @Override
    protected void logException(Exception ex, HttpServletRequest request) {
        logger.error(buildLogMessage(ex, request), ex);
    }
}
