package harugreen.harugreenserver.service;

import harugreen.harugreenserver.config.oauth2.jwt.JwtTokenProvider;
import harugreen.harugreenserver.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    public UserService(UserRepository userRepository, JwtTokenProvider jwtTokenProvider,
            AuthenticationManagerBuilder authenticationManagerBuilder) {
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
    }



    public boolean checkDuplicateUser(String email) {
        return userRepository.existsUserByEmail(email);
    }

}
