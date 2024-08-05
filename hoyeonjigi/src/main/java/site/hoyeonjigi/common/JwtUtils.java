package site.hoyeonjigi.common;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import site.hoyeonjigi.common.exception.JwtRuntimeException;
import site.hoyeonjigi.entity.member.Member;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Key;
import java.util.*;

@Slf4j
@Component
public class JwtUtils {

    private final Key key;

    private final RedisUtils redisUtils;

    @Value("${jwt.token.access-expiration-time}")
    private long accessExpirationTime;
    @Value("${jwt.token.refresh-expiration-time}")
    private long refreshExpirationTime;

    public JwtUtils(@Value("${jwt.secret}") String keyPath, RedisUtils redisUtils) throws IOException {
        String secretKey = new String(Files.readAllBytes(Paths.get(keyPath)));
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.redisUtils = redisUtils;
    }

    public String createAccessToken(Member member){

        Date now = new Date();
        Date expireDate = new Date(now.getTime() + accessExpirationTime);

        return Jwts.builder()
                .header().type("Bearer").and() // 인증 타입 지정 : Bearer (JWT 혹은 OAuth에 대한 토큰을 사용한다. (RFC 6750))
                .subject(member.getLoginId()) // 토큰 제목 : 발급 요청한 유저 이름
                .claim("role", member.getRole())
                .issuedAt(now) // 토큰 발급 날짜
                .expiration(expireDate) // 토큰 유효 기간
                .signWith(key) // 비밀키로 서명
                .compact();
    }

    public String createRefreshToken(Member member){

        Date now = new Date();
        Date expireDate = new Date(now.getTime() + refreshExpirationTime);

        String refreshToken = Jwts.builder()
                .header().type("Bearer").and() // 인증 타입 지정 : Bearer (JWT 혹은 OAuth에 대한 토큰을 사용한다. (RFC 6750))
                .subject(member.getLoginId()) // 토큰 제목 : 발급 요청한 유저 이름
                .issuedAt(now) // 토큰 발급 날짜
                .expiration(expireDate) // 토큰 유효 기간
                .signWith(key) // 비밀키로 서명
                .compact();

        // redis에 저장
        redisUtils.setData(
                member.getLoginId(),
                refreshToken,
                refreshExpirationTime);

        return refreshToken;
    }

    // SecretKey를 사용해 검증 후 Token Parsing
    public Jws<Claims> extractClaims(String token) {
        return Jwts.parser().verifyWith((SecretKey) key).build().parseSignedClaims(token);
    }

    // 토큰 정보를 검증하는 메서드
    public boolean validateToken(String token) {
        try {
            extractClaims(token);
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT Token", e);
            throw new JwtRuntimeException("유효하지 않은 JWT입니다.");
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT Token", e);
            throw new JwtRuntimeException("만료된 JWT입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT Token", e);
            throw new JwtRuntimeException("지원하지 않는 JWT입니다.");
        } catch (IllegalArgumentException e) {
            log.info("JWT claims string is empty.", e);
            throw new JwtRuntimeException("적절하지 않은 JWT입니다.");
        }
        return true;
    }
}
