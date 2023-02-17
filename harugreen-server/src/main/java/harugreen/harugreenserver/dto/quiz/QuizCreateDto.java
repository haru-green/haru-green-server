package harugreen.harugreenserver.dto.quiz;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuizCreateDto {

    private Integer id;
    private String title; //퀴즈 내용
    private String commentary; //퀴즈 해설
    private Boolean ox; //이 퀴즈의 정답이 O인지 X인지
    private Integer level; //퀴즈의 레벨

}
