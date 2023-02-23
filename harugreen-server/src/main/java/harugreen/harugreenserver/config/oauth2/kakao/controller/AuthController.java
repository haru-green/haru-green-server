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

    //kakao auth 서버에서 인가 코드를 담아서 보내주는 redirect uri.
    //여기서 다시 클라이언트 uri로 인가 코드를 담아서 보내준다.
    @GetMapping("/login/kakao")
    public String redirectToUserLogin(@RequestParam String code) {
        return "redirect:http://43.201.16.105:8080/user/login?code=" + code;
    }

}
