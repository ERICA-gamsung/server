package com.erica.gamsung.oauth2;

import com.erica.gamsung.oauth2.domain.OAuth2Memo;
import com.erica.gamsung.oauth2.repository.Oauth2MemoRepository;
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
    private final Oauth2MemoRepository oauth2MemoRepository;
    public CustomAuthorizationRequestResolver(ClientRegistrationRepository clientRegistrationRepository, String authorizationRequestBaseUri, Oauth2MemoRepository oauth2MemoRepository){
        this.oauth2MemoRepository = oauth2MemoRepository;
        defaultAuthorizationRequestResolver = new DefaultOAuth2AuthorizationRequestResolver(clientRegistrationRepository,authorizationRequestBaseUri);
        this.clientRegistrationRepository = clientRegistrationRepository;
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
        additionalParameters.put("test", request.getParameter("test"));
        OAuth2AuthorizationRequest oAuth2AuthorizationRequest = OAuth2AuthorizationRequest.from(authorizationRequest)
                .additionalParameters(additionalParameters)
                .authorizationUri("https://www.facebook.com/v19.0/dialog/oauth?test="+request.getParameter("test"))
                .build();
        OAuth2Memo oAuth2Memo = OAuth2Memo.builder().clientId(oAuth2AuthorizationRequest.getClientId()).uuid(request.getParameter("test")).build();
        oauth2MemoRepository.save(oAuth2Memo);
        return oAuth2AuthorizationRequest;
    }
}