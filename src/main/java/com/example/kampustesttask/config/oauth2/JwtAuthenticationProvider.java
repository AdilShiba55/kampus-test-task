package com.example.kampustesttask.config.oauth2;

import com.example.kampustesttask.dto.UserPrincipal;
import com.example.kampustesttask.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtValidationException;
import org.springframework.security.oauth2.server.resource.BearerTokenAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final JwtDecoder jwtDecoder;
    private UserService userService;

    public JwtAuthenticationProvider(JwtDecoder jwtDecoder, UserService userService) {
        this.jwtDecoder = jwtDecoder;
        this.userService = userService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        BearerTokenAuthenticationToken token = (BearerTokenAuthenticationToken) authentication;

        Jwt jwt = this.jwtDecoder.decode(token.getToken());
        Map<String, Object> claims = jwt.getClaims();
        String email = (String) claims.get("email");
        Long userId = userService.getByEmailAndSaveIfNotExists(email);

        List<GrantedAuthority> authorities = new ArrayList<>();

        UserPrincipal userPrincipal = new UserPrincipal();
        userPrincipal.setId(userId);
        userPrincipal.setEmail(email);
        userPrincipal.setToken(jwt.getTokenValue());

        return new UsernamePasswordAuthenticationToken(userPrincipal, null,  authorities);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(BearerTokenAuthenticationToken.class);
    }

}
