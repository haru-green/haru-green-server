package harugreen.harugreenserver.controller;

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

    @GetMapping("/login/kakao")
    public String getToken(@RequestParam String code) {
        log.info("인가 코드={}", code);
        return "hello";
    }

}
