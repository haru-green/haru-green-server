package harugreen.harugreenserver.controller;

import harugreen.harugreenserver.config.oauth2.jwt.JwtProvider;
import harugreen.harugreenserver.dto.quiz.QuizResponseDto;
import harugreen.harugreenserver.dto.user.UserResponseDto;
import harugreen.harugreenserver.service.QuizService;
import harugreen.harugreenserver.service.UserService;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/quiz")
public class QuizController {

    private final QuizService quizService;
    private final UserService userService;
    private final JwtProvider jwtProvider;

    @GetMapping
    @ResponseBody
    public List<QuizResponseDto> getQuizList(@RequestParam String email, HttpServletRequest request,
            HttpServletResponse response) {
        if (userService.isExistUserByEmail(email)) {
            if (tokenStateValidate(email, request, response)) {
                return null;
            }
            return quizService.getQuizListByLevel(email);
        }

        return null;
    }

    @PostMapping("/try")
    public UserResponseDto tryQuiz(@RequestBody HashMap<String, Boolean> quiz, @RequestParam String email, HttpServletRequest request, HttpServletResponse response) {
        if (userService.isExistUserByEmail(email)) {
            if (tokenStateValidate(email, request, response)) {
                return null;
            }
            return quizService.getAnswerByLevel(email, quiz);
        }

        return null;
    }

    @GetMapping("/answer")
    @ResponseBody
    public List<QuizResponseDto> getAnswerList(@RequestParam String email, HttpServletRequest request, HttpServletResponse response) {
        if (userService.isExistUserByEmail(email)) {
            if (tokenStateValidate(email, request, response)) {
                return null;
            }
            return quizService.getQuizListByLevel(email);
        }

        return null;
    }

    private boolean tokenStateValidate(@RequestParam String email,
            HttpServletRequest request, HttpServletResponse response) {
        String tokenState = jwtProvider.validateAccessJwt(request);
        if (tokenState.equals("EXPIRED")) {
            log.info("ACCESS TOKEN EXPIRED. refresh token 확인 필요.");
            if (!jwtProvider.validateRefreshJwt(request)) {
                log.info("REFRESH TOKEN EXPIRED. 토큰 만료. 재로그인.");
                return true;
            } else {
                log.info("ACCESS TOKEN 재발급");
                response.setHeader("X-AUTH-TOKEN", jwtProvider.reGenerateAccessToken(email));
            }
        } else if (tokenState.equals("NOT_FOUND")) {
            log.info("ACCESS TOKEN NOT FOUND.");
            return true;
        }
        log.info("ACCESS TOKEN VALIDATE. quiz list 반환.");
        return false;
    }

}
