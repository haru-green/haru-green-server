package harugreen.harugreenserver.dto.quiz;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuizResponseDto {

    private Integer id;
    private String title; //퀴즈 내용
    private String commentary; //퀴즈 해설
    private Boolean ox; //이 퀴즈의 정답이 O인지 X인지
    private Integer level; //퀴즈의 레벨
    private Integer num; //퀴즈 번호
}
