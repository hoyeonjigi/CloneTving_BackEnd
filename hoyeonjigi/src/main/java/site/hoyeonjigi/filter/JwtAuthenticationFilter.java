package site.hoyeonjigi.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;
import site.hoyeonjigi.common.JwtUtils;
import site.hoyeonjigi.service.CustomUserDetailsService;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = request.getHeader("Authorization");

        //JWT가 헤더에 있는 경우
        if (token != null && jwtUtils.extractClaims(token).getHeader().getType().equals("Bearer")) {
            //JWT 유효성 검증
            if (jwtUtils.validateToken(token)) {
                //토큰 유효성 검증 성공 시, 토큰에서 memberId 추출
                String memberId = jwtUtils.extractClaims(token).getPayload().getSubject();

                //유저와 토큰 일치 시 userDetails 생성
                UserDetails userDetails = customUserDetailsService.loadUserByUsername(memberId);

                if (userDetails != null) {
                    //UserDetsils, Password, Role -> 접근권한 인증 Token 생성
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                    //현재 Request의 Security Context에 접근권한 설정
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }
        }

        filterChain.doFilter(request, response); // 다음 필터로 넘기기
    }
}
