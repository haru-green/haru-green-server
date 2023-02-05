package harugreen.harugreenserver.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.Getter;

@Entity
@Getter
public class User {

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    @OneToMany(mappedBy = "user")
    private List<Quiz> quizList = new ArrayList<>(); //유저가 푼 퀴즈 리스트

    private String nickname; //유저 이름 (카카오 닉네임으로 할 에정)
    private Integer level; //유저의 레벨

    public User(String nickname, Integer level) {
        this.nickname = nickname;
        this.level = level;
    }

    protected User() {}
}
