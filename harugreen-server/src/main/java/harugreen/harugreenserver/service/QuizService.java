package harugreen.harugreenserver.service;

import harugreen.harugreenserver.domain.QuizInfo;
import harugreen.harugreenserver.dto.quiz.QuizCreateDto;
import harugreen.harugreenserver.dto.quiz.QuizResponseDto;
import harugreen.harugreenserver.repository.QuizInfoRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class QuizService {

    private final QuizInfoRepository quizRepository;
    private final UserService userService; //service에서 다른 service를 호출하는 것이 맞나... 고찰...
    private final ModelMapper mapper = new ModelMapper();

    @Transactional(readOnly = true)
    public List<QuizResponseDto> getQuizListByLevel(Integer level) {
        return quizRepository.findAll()
                .stream()
                .filter(q -> q.getLevel() == level)
                .map(o -> mapper.map(o, QuizResponseDto.class))
                .collect(Collectors.toList());
    }


}
