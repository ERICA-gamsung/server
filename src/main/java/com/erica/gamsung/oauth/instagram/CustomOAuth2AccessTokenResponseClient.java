//package com.erica.gamsung.oauth.instagram;
//import org.springframework.http.RequestEntity;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
//import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
//import org.springframework.security.oauth2.core.OAuth2AuthorizationException;
//import org.springframework.security.oauth2.core.OAuth2Error;
//import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
//import org.springframework.stereotype.Component;
//import org.springframework.util.Assert;
//import org.springframework.web.client.RestClientException;
//import org.springframework.web.client.RestOperations;
//import org.springframework.web.client.RestTemplate;
//import java.util.HashMap;
//import java.util.Map;
//import static org.springframework.security.oauth2.core.OAuth2AccessToken.TokenType.BEARER;
//import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequestEntityConverter;
//import org.springframework.core.convert.converter.Converter;
//
//@Component
//public class CustomOAuth2AccessTokenResponseClient implements OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest> {
//    private RestOperations restOperations;
//    private OAuth2AuthorizationCodeGrantRequestEntityConverter oAuth2AuthorizationCodeGrantRequestEntityConverter = new OAuth2AuthorizationCodeGrantRequestEntityConverter();
//    private static final String INVALID_TOKEN_RESPONSE_ERROR_CODE = "invalid_token_response";
//    @Override
//    public OAuth2AccessTokenResponse getTokenResponse(OAuth2AuthorizationCodeGrantRequest authorizationCodeGrantRequest) {
//        Assert.notNull(authorizationCodeGrantRequest, "authorizationCodeGrantRequest cannot be null");
//
//        // 인스타 토큰값을 구하기 위한 if 문 추가
//        if (authorizationCodeGrantRequest.getClientRegistration().getClientName().equals("Instagram"))
//            return getInstagramTokenResponse(authorizationCodeGrantRequest);
//        RequestEntity<?> request = this.oAuth2AuthorizationCodeGrantRequestEntityConverter.convert(authorizationCodeGrantRequest);
//        ResponseEntity<OAuth2AccessTokenResponse> response = getResponse(request);
//        return response.getBody();
//    }
//
//    private ResponseEntity<OAuth2AccessTokenResponse> getResponse(RequestEntity<?> request) {
//        try {
//            return this.restOperations.exchange(request, OAuth2AccessTokenResponse.class);
//        } catch (RestClientException ex) {
//            OAuth2Error oauth2Error = new OAuth2Error(INVALID_TOKEN_RESPONSE_ERROR_CODE,
//                    "An error occurred while attempting to retrieve the OAuth 2.0 Access Token Response: "
//                            + ex.getMessage(),
//                    null);
//            throw new OAuth2AuthorizationException(oauth2Error, ex);
//        }
//    }
//
//    public void setRequestEntityConverter(
//            Converter<OAuth2AuthorizationCodeGrantRequest, RequestEntity<?>> requestEntityConverter) {
//        Assert.notNull(requestEntityConverter, "requestEntityConverter cannot be null");
//        this.oAuth2AuthorizationCodeGrantRequestEntityConverter = (OAuth2AuthorizationCodeGrantRequestEntityConverter) requestEntityConverter;
//    }
//
//    public void setRestOperations(RestOperations restOperations) {
//        Assert.notNull(restOperations, "restOperations cannot be null");
//        this.restOperations = restOperations;
//    }
//
//    // 토큰 받는 로직 추가
//    public OAuth2AccessTokenResponse getInstagramTokenResponse(OAuth2AuthorizationCodeGrantRequest authorizationGrantRequest) {
//        RestTemplate restTemplate = new RestTemplate();
//        RequestEntity<?> request = this.oAuth2AuthorizationCodeGrantRequestEntityConverter.convert(authorizationGrantRequest);
//        assert request != null;
//        ResponseEntity<InstagramAccessToken> response = restTemplate.exchange(request, InstagramAccessToken.class);
//
//        InstagramAccessToken body = response.getBody();
//
//        Map<String, Object> additionalParameters = new HashMap<>();
//        assert body != null;
//        additionalParameters.put("access_token", body.getAccessToken());
//        additionalParameters.put("user_id", body.getUserId());
//
//        return OAuth2AccessTokenResponse
//                .withToken(body.getAccessToken())
//                .tokenType(BEARER)
//                .additionalParameters(additionalParameters)
//                .build();
//    }
//}