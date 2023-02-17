package harugreen.harugreenserver.config.oauth2.kakao.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequestMapping("/oauth")
public class AuthController {

    @GetMapping("/login/kakao")
    public String redirectToUserLogin(@RequestParam String code) {
        return "redirect:http://localhost:8080/user/login?code=" + code;
    }

}
