package harugreen.harugreenserver.config.oauth2.kakao.dto;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 * kakao auth 서버에게 받아올 access 정보를 담을 body
 */
@Component
public class KakaoAccessTokenRequest {

    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String client_id;

    @Value("${spring.security.oauth2.client.registration.kakao.authorization-grant-type}")
    private String grant_type;

    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String redirect_uri;

    public  MultiValueMap<String, String> withCode(String code) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

        params.add("grant_type", grant_type);
        params.add("client_id", client_id);
        params.add("redirect_uri", redirect_uri);
        params.add("code", code);

        return params;
    }

}
