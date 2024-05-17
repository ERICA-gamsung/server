package com.erica.gamsung.oauth2.service;
import com.erica.gamsung.global.config.redis.RedisUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import java.util.LinkedHashMap;
import java.util.Map;
@RequiredArgsConstructor
public class CustomAuthorizationRequestResolver implements OAuth2AuthorizationRequestResolver {
    private final OAuth2AuthorizationRequestResolver defaultAuthorizationRequestResolver;
    private final ClientRegistrationRepository clientRegistrationRepository;
    private final RedisUtils redisUtils;
    public CustomAuthorizationRequestResolver(ClientRegistrationRepository clientRegistrationRepository,
                                              String authorizationRequestBaseUri,
                                              RedisUtils redisUtils
                                              ){
        defaultAuthorizationRequestResolver = new DefaultOAuth2AuthorizationRequestResolver(clientRegistrationRepository,authorizationRequestBaseUri);
        this.clientRegistrationRepository = clientRegistrationRepository;
        this.redisUtils = redisUtils;
    }
    @Override
    public OAuth2AuthorizationRequest resolve(HttpServletRequest request) {
        OAuth2AuthorizationRequest authorizationRequest =
                this.defaultAuthorizationRequestResolver.resolve(request);
        return authorizationRequest != null ?
        customAuthorizationRequest(authorizationRequest,request) :
        null;
    }

    @Override
    public OAuth2AuthorizationRequest resolve(HttpServletRequest request, String clientRegistrationId) {
        OAuth2AuthorizationRequest authorizationRequest =
                this.defaultAuthorizationRequestResolver.resolve(request);
        return authorizationRequest != null ?
                customAuthorizationRequest(authorizationRequest,request) :
                null;
    }

    private OAuth2AuthorizationRequest customAuthorizationRequest(
            OAuth2AuthorizationRequest authorizationRequest,HttpServletRequest request) {
        Map<String, Object> additionalParameters =
                new LinkedHashMap<>(authorizationRequest.getAdditionalParameters());
        additionalParameters.put("uuid", request.getParameter("uuid"));
        OAuth2AuthorizationRequest oAuth2AuthorizationRequest = OAuth2AuthorizationRequest.from(authorizationRequest)
                .additionalParameters(additionalParameters)
                .authorizationUri("https://www.facebook.com/v19.0/dialog/oauth")
                .build();
        redisUtils.setData(oAuth2AuthorizationRequest.getClientId(),request.getParameter("uuid"),300000L);
        return oAuth2AuthorizationRequest;
    }
}