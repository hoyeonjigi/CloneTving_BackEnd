package site.hoyeonjigi.common;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import site.hoyeonjigi.common.exception.JwtRuntimeException;
import site.hoyeonjigi.entity.member.Member;
import site.hoyeonjigi.entity.member.RoleType;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@ActiveProfiles(value = {"local"})
class JwtUtilsTest {

    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    RedisUtils redisUtils;

    Member testMember;

    @BeforeEach
    public void setMember() {
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
    }

    @Test
    @DisplayName("JWT Access토큰 생성.")
    void createAccessJwt() {
        // given

        // when
        String accessToken = jwtUtils.createAccessToken(testMember);
        // then
        Jws<Claims> claimsJws = jwtUtils.extractClaims(accessToken);
        Assertions.assertThat(claimsJws.getHeader().getType()).isEqualTo("Bearer");
        Assertions.assertThat(claimsJws.getPayload().getSubject()).isEqualTo("test");

    }

    @Test
    @DisplayName("JWT Refresh토큰 생성.")
    void createRefreshJwt() {
        // given

        // when
        String refreshToken = jwtUtils.createRefreshToken(testMember);
        // then
        Jws<Claims> claimsJws = jwtUtils.extractClaims(refreshToken);
        Assertions.assertThat(claimsJws.getHeader().getType()).isEqualTo("Bearer");
        Assertions.assertThat(claimsJws.getPayload().getSubject()).isEqualTo("test");
        Assertions.assertThat(redisUtils.getData("test")).isEqualTo(refreshToken);
        redisUtils.deleteData("test");
    }

    @Test
    @DisplayName("JWT 검증")
    void jwtValidation() throws InterruptedException {
        // given
        String token1 = jwtUtils.createAccessToken(testMember);
        String token2 = null;
        // when
        boolean result = jwtUtils.validateToken(token1);
        // then
        Assertions.assertThat(result).isTrue();
        Thread.sleep(1100);
        Assertions.assertThatThrownBy(() -> jwtUtils.validateToken(token1))
                .isInstanceOf(JwtRuntimeException.class)
                .hasMessageContaining("만료된 JWT입니다.");
        Assertions.assertThatThrownBy(() -> jwtUtils.validateToken(token2))
                .isInstanceOf(JwtRuntimeException.class)
                .hasMessageContaining("적절하지 않은 JWT입니다.");
    }
}