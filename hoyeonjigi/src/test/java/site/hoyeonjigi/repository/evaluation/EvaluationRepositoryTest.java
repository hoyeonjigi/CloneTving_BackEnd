package site.hoyeonjigi.repository.evaluation;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import site.hoyeonjigi.dto.evaluation.EvaluationSortType;
import site.hoyeonjigi.dto.evaluation.ReviewRetrieveDto;
import site.hoyeonjigi.entity.Evaluation;
import site.hoyeonjigi.entity.Profile;
import site.hoyeonjigi.entity.content.Content;
import site.hoyeonjigi.entity.content.Drama;
import site.hoyeonjigi.entity.member.Member;
import site.hoyeonjigi.entity.member.RoleType;
import site.hoyeonjigi.repository.MemberRepository;
import site.hoyeonjigi.repository.ProfileRepository;
import site.hoyeonjigi.repository.content.ContentRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootTest
@ActiveProfiles(value = {"local"})
@Transactional
class EvaluationRepositoryTest {

    @Autowired
    private EvaluationRepository evaluationRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private ContentRepository contentRepository;

    Member testMember;
    Profile testProfile1;
    Profile testProfile2;
    Content testContent;

    @BeforeEach
    public void setData() {
        testMember = Member.builder()
                .loginId("test")
                .password("123")
                .role(RoleType.USER)
                .email("test@gmail.com")
                .profiles(null)
                .adultStatus(true)
                .smsAgreement(true)
                .privacyAgreement(true)
                .emailAgreement(true)
                .build();

        testProfile1 = Profile.builder()
                .profileName("testProfile1")
                .member(testMember)
                .profileImage(null)
                .child(false)
                .build();

        testProfile2 = Profile.builder()
                .profileName("testProfile2")
                .member(testMember)
                .profileImage(null)
                .child(false)
                .build();

        testContent = Drama.builder()
                .contentTitle("testContent")
                .dType("drama")
                .contentGenres(null)
                .poster(null)
                .grade(true)
                .overview(null)
                .releaseDate(LocalDate.now())
                .viewCount(0)
                .build();

        memberRepository.save(testMember);
        profileRepository.save(testProfile1);
        profileRepository.save(testProfile2);
        contentRepository.save(testContent);
    }

    @Test
    @DisplayName("콘텐츠에 해당 프로필이 등록한 평가 조회")
    void findEvaluationByContentIdAndProfileId() {
        // given
        Evaluation testEvaluation = Evaluation.builder()
                .profile(testProfile1)
                .content(testContent)
                .review("test")
                .ratingTime(LocalDateTime.now())
                .rating(3.0)
                .goodCount(0L)
                .badCount(0L)
                .build();
        evaluationRepository.save(testEvaluation);
        // when
        Evaluation findEvaluation = evaluationRepository.findEvaluationByContentIdAndProfileId(testContent.getId(), testProfile1.getId()).get();
        // then
        Assertions.assertThat(findEvaluation).isEqualTo(testEvaluation);

    }

    @Test
    @DisplayName("해당 콘텐츠에 대한 모든 평가 조회")
    void findAllEvaluationsByContentID() {
        // given
        Evaluation testEvaluation = Evaluation.builder()
                .profile(testProfile1)
                .content(testContent)
                .review("test")
                .ratingTime(LocalDateTime.now())
                .rating(3.0)
                .goodCount(0L)
                .badCount(0L)
                .build();

        evaluationRepository.save(testEvaluation);

        Pageable pageable = (Pageable) PageRequest.of(0, 20);
        // when
        Page<ReviewRetrieveDto> findAllEvaluationByContentId = evaluationRepository.findAllEvaluationsByContentId(testContent.getId(), EvaluationSortType.DEFAULT, pageable);
        // then
        Assertions.assertThat(findAllEvaluationByContentId.getContent().get(0).getEvaluationId()).isEqualTo(testEvaluation.getId());
        Assertions.assertThat(findAllEvaluationByContentId.getTotalPages()).isEqualTo(1);

    }

    @Test
    @DisplayName("해당 콘텐츠의 리뷰 평점 조회")
    void findAvgRatingScoreByContentId() {
        // given
        Evaluation testEvaluation1 = Evaluation.builder()
                .profile(testProfile1)
                .content(testContent)
                .review("test1")
                .ratingTime(LocalDateTime.now())
                .rating(3.0)
                .goodCount(0L)
                .badCount(0L)
                .build();

        Evaluation testEvaluation2 = Evaluation.builder()
                .profile(testProfile2)
                .content(testContent)
                .review("test2")
                .ratingTime(LocalDateTime.now())
                .rating(5.0)
                .goodCount(0L)
                .badCount(0L)
                .build();

        evaluationRepository.save(testEvaluation1);
        evaluationRepository.save(testEvaluation2);

        // when
        double result = evaluationRepository.findAvgRatingScoreByContentId(testContent.getId());

        // then
        Assertions.assertThat(result).isEqualTo(4.0);
    }
}