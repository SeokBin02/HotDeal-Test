package challenge18.hotdeal.domain.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginRequest {
    private Long userId;
    private Long password;
}
