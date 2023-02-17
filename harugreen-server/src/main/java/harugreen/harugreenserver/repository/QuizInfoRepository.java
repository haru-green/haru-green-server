package harugreen.harugreenserver.repository;

import harugreen.harugreenserver.domain.QuizInfo;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizInfoRepository extends JpaRepository<QuizInfo, Long> {
    Optional<QuizInfo> findByLevel(Integer level);
}
