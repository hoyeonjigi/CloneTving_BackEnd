package site.hoyeonjigi.dto.evaluation;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import site.hoyeonjigi.common.ValidEnum;

@Getter
public class EvaluationSearchCondition {

    @NotNull(message = "contentId은 필수 입력 값입니다.")
    private Long contentId;

    @ValidEnum(enumClass = EvaluationSortType.class, message = "[DEFAULT, LATEST, POPULARITY] 중에서 정확한 타입을 적어주세요.")
    private EvaluationSortType sortType;
}
