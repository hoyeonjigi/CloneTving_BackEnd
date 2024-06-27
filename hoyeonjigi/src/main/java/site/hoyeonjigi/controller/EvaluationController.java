package site.hoyeonjigi.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import site.hoyeonjigi.common.exception.IncorretSortTypeException;
import site.hoyeonjigi.dto.evaluation.*;
import site.hoyeonjigi.service.EvaluationService;

@RestController
@RequestMapping("/evaluation")
@RequiredArgsConstructor
public class EvaluationController {

    private final EvaluationService evaluationService;

    //리뷰 전체 조회 (최신순/인기순)
    @PostMapping("/retrieve")
    public ResponseEntity<Page<ReviewRetrieveDto>> evaluationsByContentId(@RequestBody @Valid EvaluationSearchCondition condition, Pageable pageable) {

        return ResponseEntity.ok(evaluationService.getAllEvaluationsByContentId(condition, pageable));
    }

    //해당 프로필의 리뷰 조회
    @GetMapping("/retrieve/by-profile")
    public ResponseEntity<ReviewRetrieveDto> evaluationByProfileId(
            @RequestParam("contentId") Long contentId,
            @RequestParam("profileId") Long profileId) {

        return ResponseEntity.ok(evaluationService.getEvaluationByProfileId(contentId, profileId));
    }

    //리뷰 작성
    @PostMapping("/register")
    public ResponseEntity<Long> registerEvaluation(@RequestBody @Valid EvaluationRegisterDto evaluationRegisterDto) {

        return ResponseEntity.status(HttpStatus.CREATED).body(evaluationService.register(evaluationRegisterDto));
    }

    //리뷰 수정
    @PatchMapping("/edit")
    public ResponseEntity<?> registerEvaluation(@RequestBody @Valid ReviewEditDto reviewEditDto) {

        evaluationService.edit(reviewEditDto);

        return ResponseEntity.ok().build();
    }

    //리뷰 삭제
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteEvaluation(@RequestParam("evaluationId") Long evaluationId) {

        evaluationService.deleteEvaluation(evaluationId);

        return ResponseEntity.ok().build();
    }

    //평균 평점 조회
    @GetMapping("/average-rating")
    public ResponseEntity<Double> averageRatingScore(@RequestParam("contentId") Long contentId) {

        double avgScore = evaluationService.getAverageRatingScore(contentId);

        return ResponseEntity.ok(avgScore);
    }
    //좋아요, 싫어요
    @PatchMapping("/popularity")
    public ResponseEntity<?> updatePopularity(@RequestBody @Valid EvaluationPopularitySetDto evaluationPopularitySetDto) {

        evaluationService.updatePopularity(evaluationPopularitySetDto);

        return ResponseEntity.ok().build();
    }
}
