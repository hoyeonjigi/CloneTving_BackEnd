package site.hoyeonjigi.dto.evaluation;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class ReviewEditDto {

    @NotNull(message = "evaluationId은 필수 입력 값입니다.")
    private Long evaluationId;

    @NotBlank(message = "review은 필수 입력 값입니다.")
    private String review;

    @NotNull(message = "rating은 필수 입력 값입니다.")
    private Double rating;
}
