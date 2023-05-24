package challenge18.hotdeal.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SignupReqeust {
    private String userId;
    private String password;
    private boolean isAdmin;
    private String adminToken;
}
