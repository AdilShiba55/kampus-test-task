package com.example.kampustesttask.config;

import com.example.kampustesttask.config.oauth2.KeycloakLogoutHandler;
import com.example.kampustesttask.config.oauth2.OAuth2UserConfig;
import com.example.kampustesttask.config.oauth2.OidcUserConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new KeycloakRoleConverter());

        http
                .cors().disable()
                .csrf().disable()

                .authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()

                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .oauth2Login(oc -> oc.userInfoEndpoint(ui -> ui
                        .oidcUserService(oidcUserConfig)
                        .userService(oAuth2UserConfig)))
                .oauth2ResourceServer()
                .jwt()
                .jwtAuthenticationConverter(jwtAuthenticationConverter);

        return http.build();
    }

    static class KeycloakRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {
        @Override
        public Collection<GrantedAuthority> convert(Jwt jwt) {
            Map<String, Object> realmAccess = (Map<String, Object>) jwt.getClaims().get("realm_access");

            if (realmAccess == null) {
                return new ArrayList<>();
            }

            List<GrantedAuthority> roles = ((List<String>) realmAccess.get("roles")).stream()
                    .map(roleName -> "ROLE_" + roleName)
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());


            return roles;
        }
    }
}
