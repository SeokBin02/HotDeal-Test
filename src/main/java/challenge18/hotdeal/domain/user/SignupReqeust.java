package challenge18.hotdeal.domain.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SignupReqeust {
    private Long userId;
    private Long password;
    private boolean isAdmin;
    private String adminToken;
}
