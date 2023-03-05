package harugreen.harugreenserver.service;

import harugreen.harugreenserver.config.oauth2.jwt.JwtProvider;
import harugreen.harugreenserver.config.oauth2.jwt.JwtToken;
import harugreen.harugreenserver.domain.User;
import harugreen.harugreenserver.dto.user.JwtResponseDto;
import harugreen.harugreenserver.dto.user.UserCreateDto;
import harugreen.harugreenserver.dto.user.UserResponseDto;
import harugreen.harugreenserver.repository.UserRepository;
import java.util.List;
import java.util.Optional;
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

    @Transactional(readOnly = true)
    public UserResponseDto getUserByRefreshToken(String token) {
        String email = jwtProvider.getUserEmailByDecodedJwt(token);
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

    public UserResponseDto createUser(UserCreateDto user) {
        User newUser = user.toEntity();
        return mapper.map(userRepository.save(newUser), UserResponseDto.class);
    }

    public void setUserRefreshToken(String email, String refreshToken) {
        User user = userRepository.findByEmail(email).get();
        user.updateRefreshToken(refreshToken);
    }
}
