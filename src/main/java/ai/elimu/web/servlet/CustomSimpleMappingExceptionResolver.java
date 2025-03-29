package ai.elimu.web.servlet;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

@Slf4j
public class CustomSimpleMappingExceptionResolver extends SimpleMappingExceptionResolver {
    
    @Override
    protected void logException(Exception ex, HttpServletRequest request) {
        log.error(buildLogMessage(ex, request), ex);
    }
}
