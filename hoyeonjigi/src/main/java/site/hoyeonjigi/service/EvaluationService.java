package site.hoyeonjigi.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.hoyeonjigi.common.exception.DuplicateResourceException;
import site.hoyeonjigi.dto.evaluation.*;
import site.hoyeonjigi.entity.Evaluation;
import site.hoyeonjigi.entity.Profile;
import site.hoyeonjigi.entity.content.Content;
import site.hoyeonjigi.repository.ProfileRepository;
import site.hoyeonjigi.repository.content.ContentRepository;
import site.hoyeonjigi.repository.evaluation.EvaluationRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class EvaluationService {

    private final EvaluationRepository evaluationRepository;
    private final ProfileRepository profileRepository;
    private final ContentRepository contentRepository;

    public Page<ReviewRetrieveDto> getAllEvaluationsByContentId(EvaluationSearchCondition condition, Pageable pageable) {

        return evaluationRepository.findAllEvaluationsByContentId(condition, pageable);
    }

    @Transactional
    public Long register(EvaluationRegisterDto evaluationRegisterDto) {

        Profile profile = profileRepository.findById(evaluationRegisterDto.getProfileId())
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 프로필입니다."));

        Content content = contentRepository.findById(evaluationRegisterDto.getContentId())
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 컨텐츠입니다."));

        Evaluation evaluation = Evaluation.builder()
                .profile(profile)
                .content(content)
                .review(evaluationRegisterDto.getReview())
                .rating(evaluationRegisterDto.getRating())
                .ratingTime(LocalDateTime.now())
                .goodCount(0L)
                .badCount(0L)
                .build();

        try {
            return evaluationRepository.save(evaluation).getId();
        } catch (Exception e) {
            throw new DuplicateResourceException("이미 등록한 리뷰가 존재합니다.");
        }
    }

    public ReviewRetrieveDto getEvaluationByProfileId(Long contentId, Long profileId) {

        Evaluation evaluation = evaluationRepository.findByContentIdAndProfileId(contentId, profileId)
                .orElseThrow(() -> new UsernameNotFoundException("리뷰가 존재하지 않습니다."));

        return ReviewRetrieveDto.builder()
                .evaluationId(evaluation.getId())
                .profileName(evaluation.getProfile().getProfileName())
                .review(evaluation.getReview())
                .rating(evaluation.getRating())
                .ratingTime(evaluation.getRatingTime())
                .goodCount(evaluation.getGoodCount())
                .badCount(evaluation.getBadCount())
                .build();
    }

    @Transactional
    public void edit(ReviewEditDto reviewEditDto) {
        Evaluation evaluation = evaluationRepository.findById(reviewEditDto.getEvaluationId())
                .orElseThrow(() -> new UsernameNotFoundException("리뷰가 존재하지 않습니다."));

        evaluation.editReview(reviewEditDto.getReview(), reviewEditDto.getRating());
    }

    @Transactional
    public void deleteEvaluation(Long evaluationId) {

        evaluationRepository.deleteById(evaluationId);
    }

    public double getAverageRatingScore(Long contentId) {

        return evaluationRepository.findAvgRatingScoreByContentId(contentId);
    }

    @Transactional
    public void updatePopularity(EvaluationPopularitySetDto evaluationPopularitySetDto) {

        Evaluation evaluation = evaluationRepository.findById(evaluationPopularitySetDto.getEvaluationId())
                .orElseThrow(() -> new UsernameNotFoundException("리뷰가 존재하지 않습니다."));

        EvaluationCountType countType = evaluationPopularitySetDto.getCountType();

        if (countType.equals(EvaluationCountType.GOOD)) {
            if (evaluationPopularitySetDto.getActivation()) {
                evaluation.decreaseGoodCount();
            } else {
                evaluation.increaseGoodCount();
            }
        } else if (countType.equals(EvaluationCountType.BAD)) {
            if (evaluationPopularitySetDto.getActivation()) {
                evaluation.decreaseBadCount();
            } else {
                evaluation.increaseBadCount();
            }
        } else {
            throw new IllegalArgumentException("잘못된 요청입니다.");
        }
    }
}
