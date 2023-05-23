package challenge18.hotdeal.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("users")
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public void signup(@RequestBody SignupReqeust reqeust){
        userService.signup(reqeust);
    }

    @PostMapping("/login")
    public void login(@RequestBody LoginRequest request){
        userService.login(request);
    }
}
