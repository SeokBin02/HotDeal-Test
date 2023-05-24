package challenge18.hotdeal.domain.user.controlloer;

import challenge18.hotdeal.common.util.Message;
import challenge18.hotdeal.domain.user.service.UserService;
import challenge18.hotdeal.domain.user.dto.LoginRequest;
import challenge18.hotdeal.domain.user.dto.SignupReqeust;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<Message> signup(@RequestBody SignupReqeust reqeust){
        return userService.signup(reqeust);
    }

    @PostMapping("/login")
    public ResponseEntity<Message> login(@RequestBody LoginRequest request, HttpServletResponse response){
        return userService.login(request, response);
    }
}
