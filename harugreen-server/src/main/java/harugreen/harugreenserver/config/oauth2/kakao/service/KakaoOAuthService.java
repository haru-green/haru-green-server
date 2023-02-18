package harugreen.harugreenserver.config.oauth2.kakao.service;

import harugreen.harugreenserver.config.oauth2.kakao.dto.KakaoAccessTokenRequest;
import harugreen.harugreenserver.config.oauth2.kakao.dto.KakaoAccessTokenResponse;
import harugreen.harugreenserver.config.oauth2.kakao.dto.UserInfoResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoOAuthService {

    private final KakaoAccessTokenRequest kakaoAccessTokenRequest;

    /**
     * 인가 코드를 받아서 kakao accessToken 을 발급. 하나의 Http object 를 만들어 kakao auth 서버로 요청 -> access token 받아오기.
     * https://developers.kakao.com/docs/latest/ko/kakaologin/rest-api#request-token
     */
    public KakaoAccessTokenResponse getAccessToken(String code) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> params = kakaoAccessTokenRequest.withCode(code);

        RestTemplate rt = new RestTemplate();
        HttpEntity<MultiValueMap<String, String>> accessTokenRequest = new HttpEntity<>(params, headers);

        return rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                accessTokenRequest,
                KakaoAccessTokenResponse.class
        ).getBody();
    }

    /**
     * kakao access token 으로 유저 정보 받아오기.
     * https://developers.kakao.com/docs/latest/ko/kakaologin/rest-api#req-user-info
     */
    public UserInfoResponse getUserInfo(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
        headers.add(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=utf-8");

        RestTemplate rt = new RestTemplate();
        HttpEntity<MultiValueMap<String, String>> userInfoRequest = new HttpEntity<>(headers);

        return rt.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.GET,
                userInfoRequest,
                UserInfoResponse.class
        ).getBody();
    }

}
