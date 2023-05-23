package challenge18.hotdeal.domain.user;

import challenge18.hotdeal.common.Enum.UserRole;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Entity(name = "users")
@NoArgsConstructor
@Getter
public class User {
    @Id
    private Long userId;

    @Column(nullable = false)
    private Long password;

    private UserRole role;

    public User(Long userId, Long password, UserRole role) {
        this.userId = userId;
        this.password = password;
        this.role = role;
    }
}
