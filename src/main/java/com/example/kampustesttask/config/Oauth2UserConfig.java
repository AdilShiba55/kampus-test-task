package com.example.kampustesttask.config;

import com.example.kampustesttask.dto.UserPrincipal;
import com.example.kampustesttask.entity.User;
import com.example.kampustesttask.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

@Configuration
public class Oauth2UserConfig extends OidcUserService {

    @Autowired
    private UserService userService;

    // в контекст передаем удобный нам класс пользователя
    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        OidcUser oidcUser = super.loadUser(userRequest);

        User user = userService.getByEmail(oidcUser.getEmail());
        UserPrincipal userPrincipal = new UserPrincipal();
        userPrincipal.setId(user.getId());
        userPrincipal.setToken(oidcUser.getIdToken().getTokenValue());
        userPrincipal.setName(oidcUser.getGivenName());
        userPrincipal.setIsEmailVerified(userPrincipal.getIsEmailVerified());
        userPrincipal.setEmail(oidcUser.getEmail());

        return userPrincipal;
    }
}
