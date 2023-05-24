package challenge18.hotdeal.domain.user.entity;

import challenge18.hotdeal.common.Enum.UserRole;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "users")
@NoArgsConstructor
@Getter
public class User {
    @Id
    private String userId;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    public User(String userId, String password, UserRole role) {
        this.userId = userId;
        this.password = password;
        this.role = role;
    }
}
