package site.hoyeonjigi.repository.evaluation;

import org.springframework.data.jpa.repository.JpaRepository;
import site.hoyeonjigi.entity.Evaluation;

import java.util.Optional;

public interface EvaluationRepository extends JpaRepository<Evaluation, Long>, EvaluationRepositoryCustom {

    Optional<Evaluation> findByContentIdAndProfileId(Long contentId, Long profileId);
}
