package site.hoyeonjigi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.hoyeonjigi.entity.member.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByLoginId(String loginId);

    Boolean existsByLoginId(String loginId);

    Long deleteByLoginId(String loginId);
}
