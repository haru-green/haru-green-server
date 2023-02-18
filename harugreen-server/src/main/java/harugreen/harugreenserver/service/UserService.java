package harugreen.harugreenserver.service;

import harugreen.harugreenserver.config.oauth2.jwt.JwtProvider;
import harugreen.harugreenserver.config.oauth2.jwt.JwtToken;
import harugreen.harugreenserver.domain.User;
import harugreen.harugreenserver.dto.user.JwtResponseDto;
import harugreen.harugreenserver.dto.user.UserCreateDto;
import harugreen.harugreenserver.dto.user.UserResponseDto;
import harugreen.harugreenserver.repository.UserRepository;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final ModelMapper mapper = new ModelMapper();

    public boolean isExistUserByEmail(String email) {
        return userRepository.existsUserByEmail(email);
    }

    @Transactional(readOnly = true)
    public UserResponseDto getUserByEmail(String email) {
        User user = userRepository.findByEmail(email).get();
        return mapper.map(user, UserResponseDto.class);
    }

    public UserResponseDto levelUp(String email) {
        User user = userRepository.findByEmail(email).get();
        Integer level = user.getLevel();
        if (level < 8) {
            user.setLevel(level + 1);
        }
        return mapper.map(user, UserResponseDto.class);
    }

    public JwtToken generateToken(String email) {
        return jwtProvider.createToken(email);
    }

    public UserResponseDto createUser(UserCreateDto user) {
        User newUser = user.toEntity();
        return mapper.map(userRepository.save(newUser), UserResponseDto.class);
    }

    public void setUserRefreshToken(String email, String refreshToken) {
        User user = userRepository.findByEmail(email).get();
        user.updateRefreshToken(refreshToken);
    }

    /**
     * jwt 검증로직. /user/login 을 제외한 모든 API에 적용.
     */
    public String validateJwt(HttpServletRequest request) {
        String token = jwtProvider.resolveAccessToken(request);
        if (token == null) {
            log.info("JWT ACCESS TOKEN INVALID (NOT FOUND)");
            return "NOT_FOUND";
        }

        if(!jwtProvider.validateToken(token)) {
            log.info("JWT ACCESS TOKEN EXPIRED (재발급 필요)");
            return "EXPIRED";
        }

        log.info("JWT ACCESS TOKEN VALIDATE");
        return "VALIDATE";
    }

     public boolean validateRefreshJwt(HttpServletRequest request) {
        String refreshToken = jwtProvider.resolveRefreshToken(request);
        return jwtProvider.validateToken(refreshToken);
     }

    public boolean checkDuplicateUser(String email) {
        return userRepository.existsUserByEmail(email);
    }



}
