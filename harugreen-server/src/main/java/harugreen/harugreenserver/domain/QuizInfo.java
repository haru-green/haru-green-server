package harugreen.harugreenserver.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class QuizInfo {

    @Id
    @Column(name = "quiz_id")
    private Integer id;

    private String title; //퀴즈 내용
    private String commentary; //퀴즈 해설
    private Boolean ox; //이 퀴즈의 정답이 O인지 X인지
    private Integer level; //퀴즈의 레벨
    private Boolean isSolved; //퀴즈가 풀렸는지 여부
}
