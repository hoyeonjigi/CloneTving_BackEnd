package site.hoyeonjigi.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ActiveProfiles;
import site.hoyeonjigi.dto.evaluation.EvaluationRegisterDto;
import site.hoyeonjigi.dto.member.MemberLoginDto;
import site.hoyeonjigi.entity.Evaluation;
import site.hoyeonjigi.entity.Profile;
import site.hoyeonjigi.entity.content.Content;
import site.hoyeonjigi.entity.content.Drama;
import site.hoyeonjigi.entity.member.Member;
import site.hoyeonjigi.entity.member.RoleType;
import site.hoyeonjigi.repository.MemberRepository;
import site.hoyeonjigi.repository.ProfileRepository;
import site.hoyeonjigi.repository.content.ContentRepository;
import site.hoyeonjigi.repository.evaluation.EvaluationRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles(value = {"local"})
class EvaluationServiceTest {

    @Mock
    private EvaluationRepository evaluationRepository;

    @Mock
    private ProfileRepository profileRepository;

    @Mock
    private ContentRepository contentRepository;

    @InjectMocks
    private EvaluationService evaluationService;

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
    }
    
    @Test
    @DisplayName("평가 등록 시, 해당 프로필이 존재하지 않는 경우 예외 발생")
    void registerEvaluation1() {
        // given
        EvaluationRegisterDto evaluationRegisterDto = new EvaluationRegisterDto(testProfile1.getId(), testContent.getId(), "test", 3.0);
        given(profileRepository.findById(testProfile1.getId())).willReturn(Optional.empty());
        // when
        Throwable throwable = catchThrowable(() -> evaluationService.register(evaluationRegisterDto));
        // then
        assertThat(throwable)
                .isInstanceOf(UsernameNotFoundException.class)
                .hasMessageContaining("존재하지 않는 프로필입니다.");
    }

    @Test
    @DisplayName("평가 등록 시, 해당 콘텐츠가 존재하지 않는 경우 예외 발생")
    void registerEvaluation2() {
        // given
        EvaluationRegisterDto evaluationRegisterDto = new EvaluationRegisterDto(testProfile1.getId(), testContent.getId(), "test", 3.0);
        given(profileRepository.findById(evaluationRegisterDto.getProfileId())).willReturn(Optional.ofNullable(testProfile1));
        given(contentRepository.findById(evaluationRegisterDto.getContentId())).willReturn(Optional.empty());
        // when
        Throwable throwable = catchThrowable(() -> evaluationService.register(evaluationRegisterDto));
        // then
        assertThat(throwable)
                .isInstanceOf(UsernameNotFoundException.class)
                .hasMessageContaining("존재하지 않는 컨텐츠입니다.");
    }

}