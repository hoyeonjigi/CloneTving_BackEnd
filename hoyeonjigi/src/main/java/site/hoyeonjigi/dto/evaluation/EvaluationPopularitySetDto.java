package site.hoyeonjigi.dto.evaluation;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EvaluationPopularitySetDto {

    private Long evaluationId;

    private EvaluationCountType countType;

    private Boolean activation;
}
