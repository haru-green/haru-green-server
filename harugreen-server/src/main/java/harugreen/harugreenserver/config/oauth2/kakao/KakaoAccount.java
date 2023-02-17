package harugreen.harugreenserver.config.oauth2.kakao;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class KakaoAccount {

    @JsonProperty
    private Profile profile;
    @JsonProperty
    private String email;

    public String getNickName() {
        return profile.nickname;
    }

    @Override
    public String toString() {
        return "KakaoAccount{" +
                "profile=" + profile +
                ", email='" + email + '\'' +
                '}';
    }

    @NoArgsConstructor
    private static class Profile {

        @JsonProperty
        private String nickname;

        @Override
        public String toString() {
            return "Profile{" +
                    "nickname='" + nickname + '\'' +
                    '}';
        }
    }

}
