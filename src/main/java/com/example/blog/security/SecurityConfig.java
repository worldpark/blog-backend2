package com.example.blog.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@EnableWebSecurity
@RequiredArgsConstructor
@Configuration
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;

    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity security) throws Exception{

        security
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/", "/login/**", "/check-session", "/getHashTagList", "/boardImage/**"
                                , "/loginSuccess", "/error/**", "/anonymous/resource/**", "/board/getBoardList"
                                , "/board/getBoardContent").permitAll()
                        .anyRequest().hasRole("ADMIN")
                )
                .authenticationProvider(customAuthenticationProvider())
                .formLogin((formLogin) ->
                        formLogin
                                .loginProcessingUrl("/login/login-proc")
                                .defaultSuccessUrl("/loginSuccess", true)
                                .failureHandler(customAuthFailureHandler()))
                .logout((logoutConfig) ->
                        logoutConfig.logoutUrl("/logout")
                                .logoutSuccessUrl("/"))
                .exceptionHandling((exceptionConfig) ->
                        exceptionConfig.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
                .cors((corsConfig) ->
                        corsConfig.configurationSource(corsConfigurationSource()))
                .csrf((csrfConfig) ->
                        csrfConfig.disable())
        ;

        return security.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource(){

        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(Arrays.asList("Content-Type", "Authorization"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    @Bean
    CustomAuthenticationProvider customAuthenticationProvider(){
        return new CustomAuthenticationProvider(customUserDetailsService);
    }

    @Bean
    CustomAuthFailureHandler customAuthFailureHandler(){
        return new CustomAuthFailureHandler();
    }
}
