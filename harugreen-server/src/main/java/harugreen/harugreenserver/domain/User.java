package harugreen.harugreenserver.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue
    private Long id; //인가 토큰으로 받을 수 있는 id_token

    @OneToMany(mappedBy = "user")
    private List<Quiz> quizList = new ArrayList<>(); //유저가 푼 퀴즈 리스트

    private String nickname; //유저 이름 (카카오 닉네임으로 할 에정)
    private Integer level; //유저의 레벨
    private String email; //유저의 이메일 (중복 검사용)

    @Builder
    public User(Long id, String nickname, String email, Integer level) {
        this.id = id;
        this.nickname = nickname;
        this.email = email;
        this.level = level;
    }

    public User updateUser(String nickname, String email) {
        this.nickname = nickname;
        this.email = email;
        return this;
    }
}
