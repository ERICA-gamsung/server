package com.erica.gamsung.oauth2;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final OAuth2MemberService oAuth2MemberService;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.httpBasic(HttpBasicConfigurer::disable)
                .csrf(CsrfConfigurer::disable)
                .cors(Customizer.withDefaults())
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .oauth2Login(oauth2Login->oauth2Login.userInfoEndpoint(userInfoEndpointConfig -> userInfoEndpointConfig.userService(oAuth2MemberService)));
//                .authorizeHttpRequests(
//                        authorize ->authorize
//                                .requestMatchers("/h2-console/**")
//                                .permitAll()
//                )
//                .authorizeHttpRequests(
//                        authorize -> authorize
//                                .requestMatchers("/**").permitAll()
//                                .anyRequest().authenticated()
//                );
//                .authorizeRequests()
//                .requestMatchers("/private/**").authenticated() //private로 시작하는 uri는 로그인 필수
//                .anyRequest().permitAll() //나머지 uri는 모든 접근 허용
//                .and().oauth2Login()
//                .loginPage("/loginForm") //로그인이 필요한데 로그인을 하지 않았다면 이동할 uri 설정
//                .defaultSuccessUrl("/") //OAuth 구글 로그인이 성공하면 이동할 uri 설정
//                .userInfoEndpoint()//로그인 완료 후 회원 정보 받기
//                .userService(oAuth2MemberService).and().and(); //로그인 후 받아온 유저 정보 처리
        return http.build();
    }
}