package harugreen.harugreenserver.config.oauth2.kakao.service;

import harugreen.harugreenserver.config.oauth2.kakao.dto.KakaoAccessTokenRequest;
import harugreen.harugreenserver.config.oauth2.kakao.dto.KakaoAccessTokenResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class KakaoOAuthService {

    /**
     * 인가 코드를 받아서 kakao accessToken 을 발급.
     * 하나의 Http object 를 만들어 kakao auth 서버로 요청 -> access token 받아오기.
     */
    public KakaoAccessTokenResponse getAccessToken(String code) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> params = KakaoAccessTokenRequest.withCode(code);

        RestTemplate rt = new RestTemplate();
        HttpEntity<MultiValueMap<String, String>> accessTokenRequest = new HttpEntity<>(params, headers);

        return rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                accessTokenRequest,
                KakaoAccessTokenResponse.class
        ).getBody();
    }



}
