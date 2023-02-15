package harugreen.harugreenserver.controller;

import harugreen.harugreenserver.dto.user.UserResponseDto;
import harugreen.harugreenserver.repository.UserRepository;
import harugreen.harugreenserver.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.servlet.OAuth2ClientAutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    @ResponseBody
    @GetMapping("/login")
    public ResponseEntity<String> login(@RequestParam String code) {
        return ResponseEntity.ok().body(code);
    }

    @GetMapping("/email")
    public UserResponseDto getUserByEmail(@RequestParam String email) {
        if(!userService.isExistUserByEmail(email)) {
            return null;
        }
        return userService.getUserByEmail(email);
    }

}
