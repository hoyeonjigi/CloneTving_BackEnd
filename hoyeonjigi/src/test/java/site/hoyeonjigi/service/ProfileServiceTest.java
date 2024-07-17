package site.hoyeonjigi.service;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import site.hoyeonjigi.common.exception.DuplicateResourceException;
import site.hoyeonjigi.dto.profile.ProfileDto;
import site.hoyeonjigi.dto.profile.ProfileEditDto;
import site.hoyeonjigi.dto.profile.ProfileRegisterDto;
import site.hoyeonjigi.entity.Profile;
import site.hoyeonjigi.entity.ProfileImage;
import site.hoyeonjigi.entity.member.Member;
import site.hoyeonjigi.entity.member.RoleType;
import site.hoyeonjigi.repository.MemberRepository;
import site.hoyeonjigi.repository.ProfileImageRepository;
import site.hoyeonjigi.repository.ProfileRepository;

import java.util.NoSuchElementException;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


@SpringBootTest
@Transactional
@Slf4j
public class ProfileServiceTest {

    @Autowired
    ProfileService profileService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    ProfileImageRepository profileImageRepository;
    @Autowired
    ProfileRepository profileRepository;
    ProfileImage profileImage;
    Member testMember;
    Profile testProfile;
    @BeforeEach
    void initData(){
        profileImage = new ProfileImage("test","testURL","TestCategory");
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

        testProfile = new Profile(testMember,"중복",profileImage,false);
    }
    @Test
    @DisplayName("회원이 존재하지 않을시 NotFound")
    void register_NotFound_Member(){
        profileImageRepository.save(profileImage);
        ProfileRegisterDto prDto = new ProfileRegisterDto("프로필1", 1L, true);
        Optional<Member> findMember = memberRepository.findByLoginId("test");
        assertThat(findMember.isEmpty()).isTrue();
        assertThatThrownBy(() -> profileService.register("test",prDto))
                .isInstanceOf(NoSuchElementException.class)
                        .hasMessageContaining("Not Found Member");

    }

    @Test
    @DisplayName("중복 프로필 이름 등록시 DuplicateResourceException 발생")
    void register_DuplicateResourceException(){
        profileImageRepository.save(profileImage);
        memberRepository.save(testMember);
        profileRepository.save(testProfile);
        ProfileRegisterDto prDto = new ProfileRegisterDto("중복", 1L, true);
        assertThat(memberRepository.findByLoginId("test").isPresent()).isTrue();
        assertThatThrownBy(()->profileService.register("test",prDto))
                .isInstanceOf(DuplicateResourceException.class)
                .hasMessageContaining("is exists ProfileName");

    }

    @Test
    @DisplayName("프로필 이미지 없을시 Not Found 예외 발생")
    void register_NotFound_ProfileImage(){
        memberRepository.save(testMember);
        ProfileRegisterDto prDto = new ProfileRegisterDto("프로필2", 1L, true);
        assertThat(profileImageRepository.findById(1L).isEmpty()).isTrue();
        assertThatThrownBy(() -> profileService.register("test",prDto))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("Not Found ProfileImage");
    }

    @Test
    @DisplayName("존재하지 않는 프로필 수정시 NotFoundException 발생")
    void edit_NotFoundException_Profile(){
        profileImageRepository.save(profileImage);
        memberRepository.save(testMember);
        Optional<Profile> profile = profileRepository.findById(1L);
        ProfileEditDto editDto = new ProfileEditDto();
        editDto.setProfileName("프로필 수정");
        editDto.setProfileImgId(1L);
        editDto.setChild(false);
        assertThat(profile.isEmpty()).isTrue();
        assertThatThrownBy(()-> profileService.edit("test",testProfile.getId(),editDto))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("Not Found Profile");
    }

}
