package harugreen.harugreenserver.controller;

import com.nimbusds.oauth2.sdk.util.StringUtils;
import harugreen.harugreenserver.config.oauth2.jwt.JwtTokenProvider;
import java.util.Enumeration;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    @GetMapping("/token")
    public String token(HttpServletRequest request, HttpServletResponse response) {

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                log.info(cookie.toString());
            }
        } else {
            log.info("/oauth/token cookie null");
        }

        String header = response.getHeader("X-jwt-access");
        log.info("header={}", header);

        return "hello";
    }

}
