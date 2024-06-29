package site.hoyeonjigi.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import site.hoyeonjigi.dto.member.MemberLoginDto;
import site.hoyeonjigi.dto.member.MemberRegisterDto;
import site.hoyeonjigi.entity.member.Member;
import site.hoyeonjigi.repository.MemberRepository;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles(value = {"local"})
@Slf4j
class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberService memberService;

    @Spy
    private BCryptPasswordEncoder encoder;

    @Test
    @DisplayName("존재하지 않는 아이디로 로그인 시도 시, 예외 발생")
    void loginByNonExistLoginId() {
        // given
        MemberLoginDto testRequest = new MemberLoginDto("test", "123");
        given(memberRepository.findByLoginId("test")).willReturn(Optional.empty());
        // when
        Throwable throwable = catchThrowable(() -> memberService.login(testRequest));
        // then
        assertThat(throwable)
                .isInstanceOf(UsernameNotFoundException.class)
                .hasMessageContaining("존재하지 않는 ID입니다.");
    }

    @Test
    @DisplayName("올바르지 않은 비밀번호로 로그인 시도 시, 예외 발생")
    void loginByIncorrectPassword() {
        // given
        MemberLoginDto testRequest = new MemberLoginDto("test", "123");
        Member testMember = Member.builder()
                .loginId("test")
                .password(encoder.encode("1234"))
                .build();

        given(memberRepository.findByLoginId("test")).willReturn(Optional.ofNullable(testMember));
        // when
        Throwable throwable = catchThrowable(() -> memberService.login(testRequest));
        // then
        assertThat(throwable)
                .isInstanceOf(BadCredentialsException.class)
                .hasMessageContaining("비밀번호가 일치하지 않습니다.");

    }

    @Test
    @DisplayName("이미 존재하는 아이디로 회원가입 시도 시, 예외 발생")
    void registerMemberByExistLoginId() {
        // given
        MemberRegisterDto testRegisterDto = new MemberRegisterDto("test", "123", "test@gmail.com", true, true, true, true);

        given(memberRepository.existsByLoginId(testRegisterDto.getLoginId())).willReturn(true);
        // when
        Throwable throwable = catchThrowable(() -> memberService.register(testRegisterDto));
        // then
        assertThat(throwable)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("이미 존재하는 아이디입니다.");
    }
    
    @Test
    @DisplayName("토큰 재발급 시, 받은 요청 정보에서 리프레시 토큰 추출값이 null일때 예외 발생")
    void reissueToken() {
        // given
        HttpServletRequest request = new MockHttpServletRequest();
        request.setAttribute("Refresh-Token", null);
        // when
        Throwable throwable = catchThrowable(() -> memberService.reissue(request.getHeader("Refresh-Token")));
        // then
        assertThat(throwable)
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("토큰 갱신에 실패했습니다.");
    }
}