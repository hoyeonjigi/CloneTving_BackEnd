package site.hoyeonjigi.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import site.hoyeonjigi.entity.Profile;
import site.hoyeonjigi.entity.ProfileImage;
import site.hoyeonjigi.entity.member.Member;
import site.hoyeonjigi.entity.member.RoleType;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Slf4j
public class ProfileRepositoryTest {
    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    ProfileImageRepository profileImageRepository;

    Member testMember;
    Profile testProfile1;
    Profile testProfile2;
    ProfileImage testProfileImage;
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

        testProfileImage = new ProfileImage("testName1","TestUrl1","TestCategory1");
        testProfile1 = new Profile(testMember,"테스터1",testProfileImage,false);
        testProfile2 = new Profile(testMember, "테스터2", testProfileImage, false);
    }

    @Test
    @DisplayName("프로필 이름과 회원엔티티로 프로필 조회")
    void findProfileByMemberAndProfileNameTest(){
        memberRepository.save(testMember);
        profileImageRepository.save(testProfileImage);
        profileRepository.save(testProfile1);
        profileRepository.save(testProfile2);
        Optional<Profile> profile1 = profileRepository.findProfileByMemberAndProfileName(testMember, "테스터1");
        assertThat(profile1.isPresent()).isTrue();
        assertThat(profile1.get().getProfileName()).isEqualTo("테스터1");

        Optional<Profile> profile2 = profileRepository.findProfileByMemberAndProfileName(testMember, "테스터2");
        assertThat(profile2.isPresent()).isTrue();
        assertThat(profile2.get().getProfileName()).isEqualTo("테스터2");
    }

    @Test
    @DisplayName("회원 아이디로 프로필 리스트 조회")
    void findAllByMemberTest(){
        memberRepository.save(testMember);
        profileImageRepository.save(testProfileImage);
        profileRepository.save(testProfile1);
        profileRepository.save(testProfile2);

        List<Profile> profileList = profileRepository.findAllByMember(testMember.getLoginId());
        assertThat(profileList.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("회원 로그인 아이디와 프로필 아이디로 프로필 조회")
    void findProfileByLoginIdAndProfileIdTest(){
        memberRepository.save(testMember);
        profileImageRepository.save(testProfileImage);
        profileRepository.save(testProfile1);
        profileRepository.save(testProfile2);

        Optional<Profile> profile = profileRepository.findProfileByLoginIdAndProfileId(testMember.getLoginId(), testProfile1.getId());
        assertThat(profile.isPresent()).isTrue();
        assertThat(profile.get()).isEqualTo(testProfile1);
    }
}
