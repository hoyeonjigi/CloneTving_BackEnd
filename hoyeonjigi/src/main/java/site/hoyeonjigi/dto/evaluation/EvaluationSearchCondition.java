package site.hoyeonjigi.dto.evaluation;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EvaluationSearchCondition {

    private Long contentId;

    private EvaluationSortType sortType;
}
