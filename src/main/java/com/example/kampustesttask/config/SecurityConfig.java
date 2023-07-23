package com.example.kampustesttask.config;

import com.example.kampustesttask.config.oauth2.OAuth2UserConfig;
import com.example.kampustesttask.config.oauth2.OidcUserConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig {

    private OidcUserConfig oidcUserConfig;
    private OAuth2UserConfig oAuth2UserConfig;

    public SecurityConfig(OidcUserConfig oidcUserConfig, OAuth2UserConfig oAuth2UserConfig) {
        this.oidcUserConfig = oidcUserConfig;
        this.oAuth2UserConfig = oAuth2UserConfig;
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .oauth2Login(oc -> oc.userInfoEndpoint(ui -> ui
                        .oidcUserService(oidcUserConfig)
                        .userService(oAuth2UserConfig)));

        return http.build();
    }
}
