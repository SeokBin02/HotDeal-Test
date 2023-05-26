package challenge18.hotdeal.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
public class SignupRequest {
    private String userId;
    private String password;
    private boolean admin;
    private String adminToken;
}
