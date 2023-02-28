package harugreen.harugreenserver.service;

import harugreen.harugreenserver.domain.QuizInfo;
import harugreen.harugreenserver.domain.User;
import harugreen.harugreenserver.dto.quiz.QuizResponseDto;
import harugreen.harugreenserver.dto.user.UserResponseDto;
import harugreen.harugreenserver.repository.QuizInfoRepository;
import harugreen.harugreenserver.repository.UserRepository;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class QuizService {

    private final QuizInfoRepository quizRepository;
    private final UserRepository userRepository;
    private final ModelMapper mapper = new ModelMapper();


    @Transactional(readOnly = true)
    public List<QuizResponseDto> getQuizListByLevel(String email) {
        User user = userRepository.findByEmail(email).get();
        Integer level = user.getLevel();

        return quizRepository.findAll()
                .stream()
                .filter(q -> Objects.equals(q.getLevel(), level))
                .map(o -> mapper.map(o, QuizResponseDto.class))
                .collect(Collectors.toList());
    }

    public UserResponseDto getAnswerByLevel(String email, Map<String, Boolean> submitList) {
        User user = userRepository.findByEmail(email).get();
        Integer level = user.getLevel();

        List<QuizInfo> quizByLevel = quizRepository.findByLevel(level);
        boolean ok = true;

        for (QuizInfo quizInfo : quizByLevel) {
            if (quizInfo.getNum() == 1) {
                if(submitList.get("quiz1") != quizInfo.getOx()) {
                    ok = false;
                }
            } else if (quizInfo.getNum() == 2) {
                if(submitList.get("quiz2") != quizInfo.getOx()) {
                    ok = false;
                }
            } else if (quizInfo.getNum() == 3) {
                if(submitList.get("quiz3") != quizInfo.getOx()) {
                    ok = false;
                }
            }
        }

        if(ok) {
            log.info("LEVEL {} QUIZ 정답.", level);
            user.updateAnswerTime();
            user.setLevel(level + 1);
        }

        return mapper.map(user, UserResponseDto.class);
    }


}
