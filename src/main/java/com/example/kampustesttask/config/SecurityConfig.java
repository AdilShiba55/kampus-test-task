package com.example.kampustesttask.config;

import com.example.kampustesttask.config.oauth2.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig {

    private OidcUserConfig oidcUserConfig;
    private OAuth2UserConfig oAuth2UserConfig;
    private JwtAuthenticationProvider jwtAuthenticationProvider;

    public SecurityConfig(OidcUserConfig oidcUserConfig, OAuth2UserConfig oAuth2UserConfig, JwtAuthenticationProvider jwtAuthenticationProvider) {
        this.oidcUserConfig = oidcUserConfig;
        this.oAuth2UserConfig = oAuth2UserConfig;
        this.jwtAuthenticationProvider = jwtAuthenticationProvider;
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {


        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        http
                .csrf().disable()
                .authorizeRequests()
                .mvcMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .oauth2Login(oc -> oc.userInfoEndpoint(ui -> ui
                        .oidcUserService(oidcUserConfig)
                        .userService(oAuth2UserConfig)))
                .oauth2ResourceServer()
                .jwt(new JwtResourceServerCustomizer(jwtAuthenticationProvider));

        return http.build();
    }
}
