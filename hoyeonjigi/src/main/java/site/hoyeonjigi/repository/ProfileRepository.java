package site.hoyeonjigi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import site.hoyeonjigi.entity.Profile;
import site.hoyeonjigi.entity.member.Member;

import java.util.List;
import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

    @Query("select p from Profile p where p.member =:member and p.profileName =:profileName ")
    Optional<Profile> findProfileByMemberAndProfileName(@Param("member") Member member,
                                                        @Param("profileName") String profileName);

    @Query("select p from Profile p join fetch p.profileImage where p.member.loginId =:loginId")
    List<Profile> findAllByMember(@Param("loginId") String loginId);

    @Query("select p from Profile p join fetch p.member join fetch p.profileImage where p.member.loginId =:loginId and p.id =:profileId")
    Optional<Profile> findProfileByLoginIdAndProfileId(@Param("loginId") String loginId, @Param("profileId") Long profileId);
}
