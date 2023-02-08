package harugreen.harugreenserver.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Quiz {

    @Id
    @GeneratedValue
    @Column(name = "quiz_id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String title; //퀴즈 내용
    private String commentary; //퀴즈 해설
    private Boolean ox; //이 퀴즈의 정답이 O인지 X인지
    private Integer level; //퀴즈의 레벨
    private Boolean isSolved; //퀴즈가 풀렸는지 여부
}
