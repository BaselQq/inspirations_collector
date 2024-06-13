package org.example.backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConf {

    @Value("${APP_URL}")
    private String appUrl;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/users/me").authenticated()
                        .requestMatchers(HttpMethod.POST, "/add/inspiration").authenticated()
//                        .requestMatchers(HttpMethod.PUT, "/inspiration/{id}").authenticated()
//                        .requestMatchers(HttpMethod.DELETE, "/inspiration/{id}").authenticated()
                        .anyRequest().permitAll())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
                .oauth2Login(c -> c.defaultSuccessUrl(appUrl));

                return http.build();
    }
}
