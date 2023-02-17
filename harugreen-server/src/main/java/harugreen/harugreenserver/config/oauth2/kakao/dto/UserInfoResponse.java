package harugreen.harugreenserver.config.oauth2.kakao.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import harugreen.harugreenserver.config.oauth2.kakao.KakaoAccount;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * kakao access token 을 이용해서 kakao auth 서버로부터 받은 유저 정보
 */
@Getter
@NoArgsConstructor
public class UserInfoResponse {

    private Long id;

    @JsonProperty("kakao_account")
    private KakaoAccount kakaoAccount;

    @Override
    public String toString() {
        return "UserInfoResponse={"
                + "id=" + id
                + ", kakaoAccount=" + kakaoAccount
                + "}";
    }
}
