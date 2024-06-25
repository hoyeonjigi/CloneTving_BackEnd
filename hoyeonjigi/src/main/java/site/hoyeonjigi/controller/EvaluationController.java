package site.hoyeonjigi.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.hoyeonjigi.dto.evaluation.*;
import site.hoyeonjigi.service.EvaluationService;

@RestController
@RequestMapping("/evaluation")
@RequiredArgsConstructor
@Slf4j
public class EvaluationController {

    private final EvaluationService evaluationService;

    //리뷰 전체 조회 (최신순/인기순)
    @GetMapping("/retrieve")
    public ResponseEntity<Page<ReviewRetrieveDto>> evaluationsByContentId(@ModelAttribute EvaluationSearchCondition condition, Pageable pageable) {
        log.info("condition = {}, {}", condition.getContentId(), condition.getSortType());

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
    public ResponseEntity<Long> registerEvaluation(@RequestBody EvaluationRegisterDto evaluationRegisterDto) {

        return ResponseEntity.ok(evaluationService.register(evaluationRegisterDto));
    }

    //리뷰 수정
    @PatchMapping("/edit")
    public ResponseEntity<?> registerEvaluation(@RequestBody ReviewEditDto reviewEditDto) {

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
    public ResponseEntity<?> updatePopularity(@RequestBody EvaluationPopularitySetDto evaluationPopularitySetDto) {

        evaluationService.updatePopularity(evaluationPopularitySetDto);

        return ResponseEntity.ok().build();
    }
}
