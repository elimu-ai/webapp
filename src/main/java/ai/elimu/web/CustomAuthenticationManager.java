package ai.elimu.web;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Logger;
import ai.elimu.entity.contributor.Contributor;
import ai.elimu.entity.enums.Role;
import org.apache.logging.log4j.LogManager;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

public class CustomAuthenticationManager implements AuthenticationManager {
	
    private Logger logger = LogManager.getLogger();

    private final List<GrantedAuthority> AUTHORITIES = new ArrayList<GrantedAuthority>();

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        logger.info("authenticate");

        logger.info("authentication.getName(): " + authentication.getName());
        
        Contributor contributor = (Contributor) authentication.getPrincipal();
        logger.info("contributor: " + contributor);
        logger.info("contributor.getRoles(): " + contributor.getRoles());
        for (Role role : contributor.getRoles()) {
            AUTHORITIES.add(new SimpleGrantedAuthority("ROLE_" + role.toString()));
        }

        return new UsernamePasswordAuthenticationToken(authentication.getName(), authentication.getCredentials(), AUTHORITIES);
    }

    public void authenticateUser(Contributor contributor) {
        logger.info("authenticateUser");
        
        Authentication authenticationRequest = new UsernamePasswordAuthenticationToken(contributor, "PASSWORD");
        AuthenticationManager authenticationManager = new CustomAuthenticationManager();
        Authentication authenticationResult = authenticationManager.authenticate(authenticationRequest);
        SecurityContextHolder.getContext().setAuthentication(authenticationResult);
    }
}
