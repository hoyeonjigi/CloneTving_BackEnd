package site.hoyeonjigi.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import site.hoyeonjigi.common.JwtUtils;
import site.hoyeonjigi.common.RedisUtils;
import site.hoyeonjigi.dto.JsonWebTokenDto;
import site.hoyeonjigi.dto.MemberLoginDto;
import site.hoyeonjigi.dto.MemberRegisterDto;
import site.hoyeonjigi.entity.member.Member;
import site.hoyeonjigi.entity.member.RoleType;
import site.hoyeonjigi.repository.MemberRepository;

import java.util.Optional;


@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final JwtUtils jwtUtils;
    private final BCryptPasswordEncoder encoder;
    private final RedisUtils redisUtils;

    public JsonWebTokenDto login(MemberLoginDto memberLoginDto) {

        String loginId = memberLoginDto.getLoginId();
        String password = memberLoginDto.getPassword();

        Member member = memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 ID입니다."));

        if(!encoder.matches(password, member.getPassword())) {
            throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
        }

        return createJwtDto(member);
    }

    private JsonWebTokenDto createJwtDto(Member member) {
        return JsonWebTokenDto.builder()
                .accessToken(jwtUtils.createAccessToken(member))
                .refreshToken(jwtUtils.createRefreshToken(member))
                .build();
    }

    public Long register(MemberRegisterDto memberRegisterDto) throws IllegalArgumentException {

        if (memberRepository.existsByLoginId(memberRegisterDto.getLoginId())) {
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        }

        Member member = Member.builder()
                .loginId(memberRegisterDto.getLoginId())
                .password(encoder.encode(memberRegisterDto.getPassword()))
                .role(RoleType.USER)
                .email(memberRegisterDto.getEmail())
                .emailAgreement(memberRegisterDto.isEmailAgreement())
                .privacyAgreement(memberRegisterDto.isPrivacyAgreement())
                .smsAgreement(memberRegisterDto.isSmsAgreement())
                .adultStatus(memberRegisterDto.isAdultStatus())
                .build();

        return memberRepository.save(member).getId();
    }

    public JsonWebTokenDto reissue(HttpServletRequest request) {

        // 리프레쉬 토큰 추출.
        String refreshToken = request.getHeader("Refresh-Token");

        // validateToken 메서드로 토큰 유효성 검사
        if (refreshToken != null && jwtUtils.validateToken(refreshToken)) {
            // 저장된 refresh token 찾기
            String loginId = jwtUtils.extractClaims(refreshToken).getPayload().getSubject();
            if (redisUtils.getData(loginId) != null) {
                Member member = memberRepository.findByLoginId(loginId)
                        .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 ID입니다."));

                return createJwtDto(member);
            }
        }

        throw new RuntimeException("토큰 갱신에 실패했습니다.");
    }
}
