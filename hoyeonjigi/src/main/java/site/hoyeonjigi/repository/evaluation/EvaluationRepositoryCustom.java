package site.hoyeonjigi.repository.evaluation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import site.hoyeonjigi.dto.evaluation.EvaluationCountType;
import site.hoyeonjigi.dto.evaluation.ReviewRetrieveDto;
import site.hoyeonjigi.dto.evaluation.EvaluationSearchCondition;

public interface EvaluationRepositoryCustom {

    Page<ReviewRetrieveDto> findAllEvaluationsByContentId(EvaluationSearchCondition condition, Pageable pageable);

    double findAvgRatingScoreByContentId(Long contentId);

}
