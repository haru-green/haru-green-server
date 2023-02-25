package harugreen.harugreenserver.controller;

import harugreen.harugreenserver.config.oauth2.jwt.JwtProvider;
import harugreen.harugreenserver.config.oauth2.jwt.JwtToken;
import harugreen.harugreenserver.config.oauth2.kakao.KakaoAccount;
import harugreen.harugreenserver.config.oauth2.kakao.dto.KakaoAccessTokenResponse;
import harugreen.harugreenserver.config.oauth2.kakao.dto.UserInfoResponse;
import harugreen.harugreenserver.config.oauth2.kakao.service.KakaoOAuthService;
import harugreen.harugreenserver.dto.user.UserCreateDto;
import harugreen.harugreenserver.dto.user.UserResponseDto;
import harugreen.harugreenserver.repository.UserRepository;
import harugreen.harugreenserver.service.UserService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "https://harugreen.vercel.app, http://localhost:3000", allowedHeaders = "*")
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final JwtProvider jwtProvider;
    private final KakaoOAuthService kakaoOAuthService;

    @GetMapping("/test")
    public String testCheck() {
        return "ok";
    }

    /**
     * 첫 로그인 시도 또는 검증 로직에 실패해 재로그인이 필요한 경우에만 호출.
     */
    @GetMapping("/login")
    public UserResponseDto login(@RequestParam String code, HttpServletRequest request, HttpServletResponse response) {
        log.info("USER LOGIN 실행. 인가 코드={}", code);
        KakaoAccessTokenResponse accessToken = kakaoOAuthService.getAccessToken(code);
        UserInfoResponse userInfo = kakaoOAuthService.getUserInfo(accessToken.getAccessToken());

        KakaoAccount profile = userInfo.getKakaoAccount();
        String nickname = profile.getNickName();
        String email = profile.getEmail();
//        log.info("로그인 성공. nickname={}", profile.getNickName());
//        log.info("로그인 성공. email={}", profile.getEmail());

        if (userService.isExistUserByEmail(email)) {
            //가입된 유저인데 /user/login 에 진입했다 -> 토큰 만료
            //refresh token 확인하고 만료되었으면 재발급.
            if (!jwtProvider.validateRefreshJwt(request)) {
                log.info("이미 가입된 유저. 토큰 만료, 재발급 실행");
                JwtToken token = jwtProvider.createToken(email);
                response.setHeader("X-AUTH-TOKEN", token.getAccessToken());
                response.setHeader("X-AUTH-REFRESH", token.getRefreshToken());
                response.setHeader("X-AUTH-GRANT", token.getGrantType());
                userService.setUserRefreshToken(email, token.getRefreshToken()); //refresh token 갱신
            } else {
                log.info("이미 가입된 유저. 토큰 유효. access token 재발급");
                response.setHeader("X-AUTH-TOKEN", jwtProvider.reGenerateAccessToken(email));
            }
            return userService.getUserByEmail(email);
        }

        JwtToken token = jwtProvider.createToken(email);
        response.addHeader("X-AUTH-TOKEN", token.getAccessToken());
        response.addHeader("X-AUTH-REFRESH", token.getRefreshToken());
        response.addHeader("X-AUTH-GRANT", token.getGrantType());

        UserCreateDto newUser = new UserCreateDto(nickname, 1, email, token.getRefreshToken());
        return userService.createUser(newUser);
    }

    @GetMapping("/get")
    public UserResponseDto getUser(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = request.getHeader("X-AUTH-REFRESH");
        return userService.getUserByRefreshToken(refreshToken);
    }

    @PostMapping("/levelup/{email}")
    public UserResponseDto levelUp(@PathVariable String email) {

        if (!userService.isExistUserByEmail(email)) {
            return null;
        }
        return userService.levelUp(email);
    }

}
