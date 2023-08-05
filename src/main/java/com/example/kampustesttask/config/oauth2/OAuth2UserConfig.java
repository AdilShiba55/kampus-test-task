package com.example.kampustesttask.config.oauth2;

import com.example.kampustesttask.dto.UserPrincipal;
import com.example.kampustesttask.service.UserService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Map;

@Configuration
public class OAuth2UserConfig extends DefaultOAuth2UserService {

    private UserService userService;

    public OAuth2UserConfig(UserService userService) {
        this.userService = userService;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        Map<String, Object> attributes = oAuth2User.getAttributes();
        String email = (String) attributes.get("email");
        String name = (String) attributes.get("login");
        Long userId = userService.getByEmailAndSaveIfNotExists(email);
        UserPrincipal userPrincipal = new UserPrincipal();
        userPrincipal.setId(userId);
        userPrincipal.setToken(userRequest.getAccessToken().getTokenValue());
        userPrincipal.setEmail(email);
        userPrincipal.setGithub(true);

        return userPrincipal;
    }
}
