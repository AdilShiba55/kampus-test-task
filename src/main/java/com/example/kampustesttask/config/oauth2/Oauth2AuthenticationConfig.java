package com.example.kampustesttask.config.oauth2;

import com.example.kampustesttask.dto.UserPrincipal;
import com.example.kampustesttask.entity.User;
import com.example.kampustesttask.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.OffsetDateTime;

@Configuration
public class Oauth2AuthenticationConfig extends SavedRequestAwareAuthenticationSuccessHandler {

    @Autowired
    private UserService userService;

    // при успешной аутентификации сохраняем пользователя у нас, если такого ранее не было
    // также, возвращаем на адрес, куда пользователь обращался перед аутентификацией
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
