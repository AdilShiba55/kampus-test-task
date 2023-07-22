package com.example.kampustesttask.config;

import com.example.kampustesttask.dto.UserPrincipal;
import com.example.kampustesttask.entity.User;
import com.example.kampustesttask.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.HashSet;

@Configuration
public class Oauth2AuthConfig extends OidcUserService implements AuthenticationSuccessHandler {

    @Autowired
    private UserService userService;

    // в контекст кладем свой класс вместо OidcUser
    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        OidcUser oidcUser = super.loadUser(userRequest);

        UserPrincipal userPrincipal = new UserPrincipal();
        userPrincipal.setToken(oidcUser.getIdToken().getTokenValue());
        userPrincipal.setName(oidcUser.getGivenName());
        userPrincipal.setIsEmailVerified(userPrincipal.getIsEmailVerified());
        userPrincipal.setEmail(oidcUser.getEmail());

        return userPrincipal;
    }

    // Сохраняем пользователя у себя, если ранее с такой почтой не было
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        String email = userPrincipal.getEmail();
        if(userService.existsByEmail(email)) {
            Long id = userService.getByEmail(email).getId();
            userPrincipal.setId(id);
        }
        else {
            User user = new User();
            user.setEmail(email);
            user.setDtCreate(OffsetDateTime.now());
            userService.save(user);
        }
    }
}
