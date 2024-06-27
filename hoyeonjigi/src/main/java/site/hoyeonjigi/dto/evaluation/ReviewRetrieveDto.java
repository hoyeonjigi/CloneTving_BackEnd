package site.hoyeonjigi.dto.evaluation;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ReviewRetrieveDto {

    private Long evaluationId;

    private String profileName;

    private String review;

    private LocalDateTime ratingTime;

    private Long goodCount;

    private Long badCount;

    private Double rating;

    @QueryProjection
    public ReviewRetrieveDto(Long evaluationId, String profileName, String review, LocalDateTime ratingTime, Long goodCount, Long badCount, Double rating) {
        this.evaluationId = evaluationId;
        this.profileName = profileName;
        this.review = review;
        this.ratingTime = ratingTime;
        this.goodCount = goodCount;
        this.badCount = badCount;
        this.rating = rating;
    }
}
