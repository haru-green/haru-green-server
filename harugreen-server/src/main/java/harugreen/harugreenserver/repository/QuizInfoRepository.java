package harugreen.harugreenserver.repository;

import harugreen.harugreenserver.domain.QuizInfo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizInfoRepository extends JpaRepository<QuizInfo, Long> {

    List<QuizInfo> findByLevel(Integer level);
}
