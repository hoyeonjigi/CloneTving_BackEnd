package site.hoyeonjigi.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import site.hoyeonjigi.entity.member.Member;
import site.hoyeonjigi.repository.MemberRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        Member member = memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> new UsernameNotFoundException("해당하는 유저가 없습니다."));

        return User.builder()
                .username(member.getLoginId())
                .password(member.getPassword())
                .roles(String.valueOf(member.getRole()))
                .build();
    }
}
