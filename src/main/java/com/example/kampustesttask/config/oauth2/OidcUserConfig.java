package com.example.kampustesttask.config.oauth2;

import com.example.kampustesttask.dto.UserPrincipal;
import com.example.kampustesttask.entity.User;
import com.example.kampustesttask.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.time.OffsetDateTime;

@Configuration
public class OidcUserConfig extends OidcUserService {

    private UserService userService;

    public OidcUserConfig(UserService userService) {
        this.userService = userService;
    }

    // в контекст передаем удобный нам класс пользователя
    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        OidcUser oidcUser = super.loadUser(userRequest);
        String email = oidcUser.getEmail();
        Long userId = userService.getByEmailAndSaveIfNotExists(email);
        UserPrincipal userPrincipal = new UserPrincipal();
        userPrincipal.setId(userId);
        userPrincipal.setToken(oidcUser.getIdToken().getTokenValue());
        userPrincipal.setName(oidcUser.getGivenName());
        userPrincipal.setEmail(oidcUser.getEmail());

        return userPrincipal;
    }
}
