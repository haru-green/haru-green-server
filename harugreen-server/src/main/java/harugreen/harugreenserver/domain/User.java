package harugreen.harugreenserver.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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

    @OneToMany(mappedBy = "user")
    @Builder.Default
    private List<Quiz> quizList = new ArrayList<>(); //유저가 푼 퀴즈 리스트

    @Enumerated(EnumType.STRING)
    private Role role;

    private String refreshToken;

    @Builder
    public User(String nickname, Integer level, String email) {
        this.nickname = nickname;
        this.level = level;
        this.email = email;
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

    public void authorizedUser() {
        this.role = Role.USER;
    }
}
