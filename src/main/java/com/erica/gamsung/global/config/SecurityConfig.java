package com.erica.gamsung.global.config;

import com.erica.gamsung.oauth2.CustomAuthorizationRequestResolver;
import com.erica.gamsung.oauth2.OAuth2MemberService;
import com.erica.gamsung.oauth2.Oauth2SuccessfulHandler;
import com.erica.gamsung.oauth2.repository.Oauth2MemoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final OAuth2MemberService oAuth2MemberService;
    private final Oauth2SuccessfulHandler oauth2SuccessfulHandler;
    private final ClientRegistrationRepository clientRegistrationRepository;
    private final Oauth2MemoRepository oauth2MemoRepository;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.httpBasic(HttpBasicConfigurer::disable)
                .csrf(CsrfConfigurer::disable)
                .cors(Customizer.withDefaults())
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                        .oauth2Login(oauth2Login-> oauth2Login
                                .clientRegistrationRepository(this.clientRegistrationRepository)
                                .authorizationEndpoint(authorizationEndpointConfig -> authorizationEndpointConfig.authorizationRequestResolver(
                                        new CustomAuthorizationRequestResolver(
                                                this.clientRegistrationRepository,"/oauth2/authorization",
                                                oauth2MemoRepository)
                                ))
                                .userInfoEndpoint(userInfoEndpointConfig -> userInfoEndpointConfig.userService(oAuth2MemberService))
                                .successHandler(oauth2SuccessfulHandler)
                        );
//                .authorizeHttpRequests(
//                        authorize ->authorize
//                                .requestMatchers("/ws-stomp")
//                                .permitAll()
//                );
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
//https://localhost:8080/oauth2/authorization/facebook
//https://www.facebook.com/v19.0/dialog/oauth?client_id=3797036207194636&display=page&extras={"setup":{"channel":"IG_API_ONBOARDING"}}&redirect_uri=https://localhost:8080/login/oauth2/facebook
//&response_type=token&scope=instagram_basic,instagram_content_publish
//rest api : 먼저 안드로이드에서 서버 웹소켓 연결 -> 프론트엔드에서 로그인 페이지로 연결 -> 마지막에 token을 파라미터로 전달 -> token + uuid 서버로 전송 -> 서버가 받아서 구독 uri로 리턴