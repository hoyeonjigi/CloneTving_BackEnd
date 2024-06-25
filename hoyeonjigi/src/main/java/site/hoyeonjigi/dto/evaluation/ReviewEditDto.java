package site.hoyeonjigi.dto.evaluation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ReviewEditDto {

    private Long evaluationId;

    private String review;

    private Double rating;
}
