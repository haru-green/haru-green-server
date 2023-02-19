package harugreen.harugreenserver.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class QuizInfo {

    @Id
    @Column(name = "quiz_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title; //퀴즈 내용
    private String commentary; //퀴즈 해설
    private Boolean ox; //이 퀴즈의 정답이 O인지 X인지
    private Integer level; //퀴즈의 레벨
    private Integer num; //퀴즈 번호
}
