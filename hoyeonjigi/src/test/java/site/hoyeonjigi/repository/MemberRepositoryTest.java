package site.hoyeonjigi.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import site.hoyeonjigi.entity.member.Member;
import site.hoyeonjigi.entity.member.RoleType;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles(value = {"local"})
@Transactional
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

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
    @DisplayName("로그인 아이디로 맴버 조회")
    void findMemberByLoginId() {
        // given
        memberRepository.save(testMember);
        // when
        Member findMember = memberRepository.findByLoginId("test").get();
        // then
        Assertions.assertThat(findMember).isEqualTo(testMember);
    }

    @Test
    @DisplayName("로그인 아이디로 해당 맴버가 존재하는지 확인")
    void existsByLoginId() {
        // given
        memberRepository.save(testMember);
        // when
        Boolean result = memberRepository.existsByLoginId("test");
        // then
        Assertions.assertThat(result).isTrue();
    }

    @Test
    @DisplayName("로그인 아이디로 해당 맴버 삭제")
    void deleteByLoginId() {
        // given
        memberRepository.save(testMember);
        // when
        Long result = memberRepository.deleteByLoginId("test");
        // then
        Assertions.assertThat(result).isEqualTo(testMember.getId());

    }
}