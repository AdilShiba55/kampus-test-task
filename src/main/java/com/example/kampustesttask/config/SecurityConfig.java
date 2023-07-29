package com.example.kampustesttask.config;

import com.example.kampustesttask.config.oauth2.KeycloakLogoutHandler;
import com.example.kampustesttask.config.oauth2.OAuth2UserConfig;
import com.example.kampustesttask.config.oauth2.OidcUserConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig {

    private OidcUserConfig oidcUserConfig;
    private OAuth2UserConfig oAuth2UserConfig;
    private KeycloakLogoutHandler keycloakLogoutHandler;

    public SecurityConfig(OidcUserConfig oidcUserConfig, OAuth2UserConfig oAuth2UserConfig, KeycloakLogoutHandler keycloakLogoutHandler) {
        this.oidcUserConfig = oidcUserConfig;
        this.oAuth2UserConfig = oAuth2UserConfig;
        this.keycloakLogoutHandler = keycloakLogoutHandler;
    }

    @Bean
    protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        return new RegisterSessionAuthenticationStrategy(new SessionRegistryImpl());
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .mvcMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .oauth2Login(oc -> oc.userInfoEndpoint(ui -> ui
                        .oidcUserService(oidcUserConfig)
                        .userService(oAuth2UserConfig)))
                .logout()
                .addLogoutHandler(keycloakLogoutHandler)
                .logoutSuccessUrl("/")
                .and()
                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);

        return http.build();
    }
}
