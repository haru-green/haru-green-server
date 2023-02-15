package harugreen.harugreenserver.service;

import harugreen.harugreenserver.config.oauth2.jwt.JwtTokenProvider;
import harugreen.harugreenserver.domain.User;
import harugreen.harugreenserver.dto.user.UserResponseDto;
import harugreen.harugreenserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private ModelMapper mapper = new ModelMapper();

    public boolean isExistUserByEmail(String email) {
        return userRepository.existsUserByEmail(email);
    }

    public UserResponseDto getUserByEmail(String email) {
        User user = userRepository.findByEmail(email).get();
        return mapper.map(user, UserResponseDto.class);
    }



    public boolean checkDuplicateUser(String email) {
        return userRepository.existsUserByEmail(email);
    }

}
