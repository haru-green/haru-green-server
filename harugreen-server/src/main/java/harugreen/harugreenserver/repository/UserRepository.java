package harugreen.harugreenserver.repository;

import harugreen.harugreenserver.domain.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByEmail(String email);
    Optional<List<User>> findAllByRefreshToken(String refreshToken);
    boolean existsUserByEmail(String email);
}
