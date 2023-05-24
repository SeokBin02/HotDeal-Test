package challenge18.hotdeal.domain.user.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SignupRequest {
    private String userId;
    private String password;
    private boolean admin;
    private String adminToken;
}
