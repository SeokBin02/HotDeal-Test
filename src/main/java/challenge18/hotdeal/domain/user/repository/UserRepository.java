package challenge18.hotdeal.domain.user.repository;

import challenge18.hotdeal.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUserId(String userId);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "insert into users (user_id, password, role) VALUES (:userId, :password, :role)", nativeQuery = true)
    void insert(@Param("userId") String userId, @Param("password") String password, @Param("role") String role);
}
