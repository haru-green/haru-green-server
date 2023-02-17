package harugreen.harugreenserver.controller;

import com.nimbusds.oauth2.sdk.util.StringUtils;
import harugreen.harugreenserver.config.oauth2.jwt.JwtTokenProvider;
import java.util.Enumeration;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/oauth")
public class AuthController {

    private final JwtTokenProvider jwtTokenProvider;

    /**
     *
     * @param accessToken jwt accessToken
     * @param refreshToken jwt refreshToken
     * @param grant jwt grantType
     * @return access, refresh, grant 를 담은 JwtResponseDto
     */
    @GetMapping("/token")
    public String getToken(@RequestParam String accessToken, @RequestParam String refreshToken, @RequestParam String grant) {
        Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);


        return "hello";
    }

}
