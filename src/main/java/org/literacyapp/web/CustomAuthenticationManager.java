package org.literacyapp.web;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.literacyapp.model.enums.Role;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

public class CustomAuthenticationManager implements AuthenticationManager {
	
    private static Logger logger = Logger.getLogger(CustomAuthenticationManager.class);

    private final List<GrantedAuthority> AUTHORITIES = new ArrayList<GrantedAuthority>();

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
            logger.info("authenticate");

            logger.info("authentication.getName(): " + authentication.getName());

            AUTHORITIES.add(new SimpleGrantedAuthority(authentication.getName()));

            return new UsernamePasswordAuthenticationToken(authentication.getName(), authentication.getCredentials(), AUTHORITIES);
    }

    public static void authenticateUser(Role role) {
        logger.info("authenticateUser");
        Authentication authenticationRequest = new UsernamePasswordAuthenticationToken("ROLE_" + role, "PASSWORD");
        AuthenticationManager authenticationManager = new CustomAuthenticationManager();
        Authentication authenticationResult = authenticationManager.authenticate(authenticationRequest);
        SecurityContextHolder.getContext().setAuthentication(authenticationResult);
    }
}
