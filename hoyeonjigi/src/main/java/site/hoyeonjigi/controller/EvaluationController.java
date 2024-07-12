package site.hoyeonjigi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "리뷰 전체 조회", description = "해당 컨텐츠의 리뷰를 [최신순/인기순]으로 전체 조회 API")
    @ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.", content = @Content(mediaType = "application/json"))
    @Parameters({
            @Parameter(name = "contentId", description = "컨텐츠 ID 입력", example = "1"),
            @Parameter(name = "sortType", description = "[DEFAULT, LATEST, POPULARITY] 중 정렬 타입 선택", example = "DEFAULT"),
            @Parameter(name = "page", description = "조회할 페이지 번호(0부터 시작)", example = "0"),
            @Parameter(name = "size", description = "한 페이지당 최대 항목 수", example = "20")
    })
    @PostMapping("/retrieve")
    public ResponseEntity<Page<ReviewRetrieveDto>> evaluationsByContentId(@RequestBody @Valid EvaluationSearchCondition condition, Pageable pageable) {

        Long contentId = condition.getContentId();
        EvaluationSortType sortType = condition.getSortType();


        return ResponseEntity.ok(evaluationService.getAllEvaluationsByContentId(contentId, sortType, pageable));
    }

    @Operation(summary = "해당 프로필의 리뷰 조회", description = "해당 컨텐츠의 리뷰들에서 특정 프로필의 리뷰 조회 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "리뷰가 존재하지 않습니다.", content = @Content(mediaType = "application/json"))
    })
    @Parameters({
            @Parameter(name = "contentId", description = "컨텐츠 ID 입력", example = "1"),
            @Parameter(name = "profileId", description = "프로필 ID 입력", example = "1")
    })
    @GetMapping("/retrieve/by-profile")
    public ResponseEntity<ReviewRetrieveDto> evaluationByProfileId(
            @RequestParam("contentId") Long contentId,
            @RequestParam("profileId") Long profileId) {

        return ResponseEntity.ok(evaluationService.getEvaluationByProfileId(contentId, profileId));
    }

    @Operation(summary = "리뷰 작성", description = "해당 컨텐츠의 리뷰 작성 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "요청하는 데이터를 찾을 수 없습니다.", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "409", description = "이미 등록한 리뷰가 존재합니다.", content = @Content(mediaType = "application/json"))
    })
    @Parameters({
            @Parameter(name = "contentId", description = "컨텐츠 ID 입력", example = "1"),
            @Parameter(name = "profileId", description = "프로필 ID 입력", example = "1"),
            @Parameter(name = "review", description = "리뷰 내용 입력", example = "추천합니다."),
            @Parameter(name = "rating", description = "[0.5~5] 별점 입력", example = "1.5")
    })
    @PostMapping("/register")
    public ResponseEntity<Long> registerEvaluation(@RequestBody @Valid EvaluationRegisterDto evaluationRegisterDto) {

        return ResponseEntity.status(HttpStatus.CREATED).body(evaluationService.register(evaluationRegisterDto));
    }

    @Operation(summary = "리뷰 수정", description = "작성한 리뷰 수정 API")
    @ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.", content = @Content(mediaType = "application/json"))
    @Parameters({
            @Parameter(name = "evaluationId", description = "수정할 평가 ID 입력", example = "1"),
            @Parameter(name = "review", description = "수정할 리뷰 내용 입력", example = "수정합니다."),
            @Parameter(name = "rating", description = "[0.5~5] 수정할 별점 입력", example = "1.5")
    })
    @PatchMapping("/edit")
    public ResponseEntity<?> registerEvaluation(@RequestBody @Valid ReviewEditDto reviewEditDto) {

        evaluationService.edit(reviewEditDto);

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "리뷰 삭제", description = "작성한 리뷰 삭제 API")
    @ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.", content = @Content(mediaType = "application/json"))
    @Parameter(name = "evaluationId", description = "삭제할 평가 ID 입력", example = "1")
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteEvaluation(@RequestParam("evaluationId") Long evaluationId) {

        evaluationService.deleteEvaluation(evaluationId);

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "평균 별점 조회", description = "해당 컨텐츠의 별점들의 평균 조회 API")
    @ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.", content = @Content(mediaType = "application/json"))
    @Parameter(name = "contentId", description = "컨텐츠 ID 입력", example = "1")
    @GetMapping("/average-rating")
    public ResponseEntity<Double> averageRatingScore(@RequestParam("contentId") Long contentId) {

        double avgScore = evaluationService.getAverageRatingScore(contentId);

        return ResponseEntity.ok(avgScore);
    }
    //좋아요, 싫어요
    @Operation(summary = "리뷰 추천 기능", description = "리뷰의 좋아요, 싫어요 기능 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "잘못된 요청입니다.", content = @Content(mediaType = "application/json"))
    })
    @Parameters({
            @Parameter(name = "evaluationId", description = "평가 ID 입력", example = "1"),
            @Parameter(name = "countType", description = "[GOOD, BAD] 중 타입 입력", example = "GOOD"),
            @Parameter(name = "activation", description = "추천 버튼 활성화 여부", example = "true")
    })
    @PatchMapping("/popularity")
    public ResponseEntity<?> updatePopularity(@RequestBody @Valid EvaluationPopularitySetDto evaluationPopularitySetDto) {

        Long evaluationId = evaluationPopularitySetDto.getEvaluationId();
        EvaluationCountType countType = evaluationPopularitySetDto.getCountType();
        Boolean activation = evaluationPopularitySetDto.getActivation();

        evaluationService.updatePopularity(evaluationId, countType, activation);

        return ResponseEntity.ok().build();
    }
}
