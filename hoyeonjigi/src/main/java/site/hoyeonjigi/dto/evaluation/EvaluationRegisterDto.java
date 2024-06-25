package site.hoyeonjigi.dto.evaluation;


import lombok.Getter;

@Getter
public class EvaluationRegisterDto {

    private Long profileId;

    private Long contentId;

    private String review;

    private Double rating;
}
