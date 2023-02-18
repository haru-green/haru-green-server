package harugreen.harugreenserver.domain;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class User {

    private String nickname; //유저 이름 (카카오 닉네임으로 할 에정)
    private Integer level; //유저의 레벨

    @Id
    private String email; //유저의 이메일 (중복 검사용)

    private String refreshToken;
    private LocalDateTime answerTime; //문제 푼 시간. (하루에 문제 2번 못품) LocalDateTime.now()를 저장하면 됨.

    @Builder
    public User(String nickname, Integer level, String email, String refreshToken) {
        this.nickname = nickname;
        this.level = level;
        this.email = email;
        this.refreshToken = refreshToken;
    }

    public User() {}

    public User updateUser(String nickname, String email) {
        this.nickname = nickname;
        this.email = email;
        return this;
    }

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
