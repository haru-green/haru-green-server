package harugreen.harugreenserver.controller;

import com.nimbusds.oauth2.sdk.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {

    @GetMapping("/token")
    public String token(@RequestParam String token, @RequestParam String error) {
        log.info("토큰 받아오기 성공? token={}", token);
        if(StringUtils.isNotBlank(error)) {
            return error;
        } else {
            return token;
        }
    }

}
