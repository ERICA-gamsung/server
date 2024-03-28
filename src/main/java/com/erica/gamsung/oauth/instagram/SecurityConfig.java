//package com.erica.gamsung.oauth.instagram;
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//@EnableWebSecurity
//@EnableMethodSecurity(prePostEnabled = true)
//@RequiredArgsConstructor
//public class SecurityConfig {
//
//    // 새로 만든 객체를 DI 한다.
//    private final CustomOAuth2AccessTokenResponseClient oAuth2AccessTokenResponseClient;
//
//    @Bean
//    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .formLogin(
//                        formLogin -> formLogin
//                                .loginPage("/usr/member/login")
//                )
//                .oauth2Login(
//                        oauth2Login -> oauth2Login
//                                .loginPage("/usr/member/login")
//                                .tokenEndpoint(t -> t
//                                        .accessTokenResponseClient(oAuth2AccessTokenResponseClient)
//                                )
//                )
//                .logout(
//                        logout -> logout
//                                .logoutUrl("/usr/member/logout")
//                );
//
//        return http.build();
//    }
//
//    @Bean
//    PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//}