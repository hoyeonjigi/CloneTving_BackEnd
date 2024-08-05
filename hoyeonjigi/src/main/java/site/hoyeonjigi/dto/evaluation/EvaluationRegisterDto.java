package site.hoyeonjigi.dto.evaluation;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class EvaluationRegisterDto {

    @NotNull(message = "profileId은 필수 입력 값입니다.")
    private Long profileId;

    @NotNull(message = "contentId은 필수 입력 값입니다.")
    private Long contentId;

    @NotBlank(message = "review은 필수 입력 값입니다.")
    private String review;

    @NotNull(message = "rating은 필수 입력 값입니다.")
    private Double rating;

    public EvaluationRegisterDto(Long profileId, Long contentId, String review, Double rating) {
        this.profileId = profileId;
        this.contentId = contentId;
        this.review = review;
        this.rating = rating;
    }
}
