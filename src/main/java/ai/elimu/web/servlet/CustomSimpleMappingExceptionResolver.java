package ai.elimu.web.servlet;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import ai.elimu.util.DiscordHelper;
import ai.elimu.util.DiscordHelper.Channel;

@Slf4j
public class CustomSimpleMappingExceptionResolver extends SimpleMappingExceptionResolver {
    
    @Override
    protected void logException(Exception ex, HttpServletRequest request) {
        log.info("logException");

        log.error(request.toString(), ex);
        DiscordHelper.postToChannel(Channel.CONTENT, request + ": `" + ex.getClass() + ": " + ex.getMessage() + "`");
    }
}
