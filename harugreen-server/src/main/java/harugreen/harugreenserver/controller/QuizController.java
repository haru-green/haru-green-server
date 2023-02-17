package harugreen.harugreenserver.controller;

import harugreen.harugreenserver.config.oauth2.jwt.JwtProvider;
import harugreen.harugreenserver.dto.quiz.QuizResponseDto;
import harugreen.harugreenserver.service.QuizService;
import harugreen.harugreenserver.service.UserService;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/quiz")
public class QuizController {

    private final QuizService quizService;
    private final UserService userService;
    private final JwtProvider jwtProvider;

    @GetMapping
    public List<QuizResponseDto> getQuizList(@RequestParam String email, HttpServletRequest request) {
        //TODO: jwt 검증 로직
        log.info("EMAIL={}, ACCESS TOKEN 검증 시작.", email);

        return null;
    }


}
