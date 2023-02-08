package harugreen.harugreenserver.config;

import harugreen.harugreenserver.domain.User;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Builder
public class OAuth2Attributes {

    private Map<String, Object> attributes;
    private String nameAttributesKey;
    private String nickname;
    private String email;
    private String provider;

    public static OAuth2Attributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
        log.info("registrationId={}", registrationId);
        return ofKakao(userNameAttributeName, attributes);
    }


    public static OAuth2Attributes ofKakao(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> account = (Map<String, Object>) response.get("profile");

        return OAuth2Attributes.builder()
                .nickname((String) account.get("nickname"))
                .email((String) response.get("email"))
                .provider("Kakao")
                .attributes(attributes)
                .nameAttributesKey(userNameAttributeName)
                .build();
    }

    public User toEntity() {
        return User.builder()
                .nickname(nickname)
                .email(email)
                .level(1)
                .build();
    }
}
