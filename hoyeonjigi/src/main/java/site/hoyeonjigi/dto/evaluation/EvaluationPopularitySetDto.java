package site.hoyeonjigi.dto.evaluation;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import site.hoyeonjigi.common.ValidEnum;

@Getter
public class EvaluationPopularitySetDto {

    @NotNull(message = "evaluationId은 필수 입력 값입니다.")
    private Long evaluationId;

    @ValidEnum(message = "[GOOD, BAD] 중에서 정확한 타입을 적어주세요.", enumClass = EvaluationCountType.class)
    private EvaluationCountType countType;

    @NotNull(message = "activation은 필수 입력 값입니다.")
    private Boolean activation;

}
