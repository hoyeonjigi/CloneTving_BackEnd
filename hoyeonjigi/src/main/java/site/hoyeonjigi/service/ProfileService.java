package site.hoyeonjigi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.hoyeonjigi.common.exception.DuplicateResourceException;
import site.hoyeonjigi.dto.ProfileDto;
import site.hoyeonjigi.dto.ProfileEditDto;
import site.hoyeonjigi.dto.ProfileRegisterDto;
import site.hoyeonjigi.entity.Profile;
import site.hoyeonjigi.entity.ProfileImage;
import site.hoyeonjigi.entity.member.Member;
import site.hoyeonjigi.repository.MemberRepository;
import site.hoyeonjigi.repository.ProfileImageRepository;
import site.hoyeonjigi.repository.ProfileRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProfileService {

    private final MemberRepository memberRepository;
    private final ProfileRepository profileRepository;
    private final ProfileImageRepository profileImageRepository;
    public ProfileDto register(String loginId, ProfileRegisterDto profileRegisterDto){
        Member member = memberRepository.findByLoginId(loginId).orElseThrow(() ->
                new NoSuchElementException("Not Found Member"));

        Optional<Profile> profileByMemberAndProfileName =
                profileRepository.findProfileByMemberAndProfileName(member, profileRegisterDto.getProfileName());
        if(profileByMemberAndProfileName.isPresent()){
            throw new DuplicateResourceException("is exists ProfileName");
        }

        ProfileImage profileImage = profileImageRepository.findById(profileRegisterDto.getProfileImgId()).orElseThrow(() ->
                new NoSuchElementException("Not Found ProfileImage"));
        Profile profile = new Profile(
                member,
                profileRegisterDto.getProfileName(),
                profileImage,
                profileRegisterDto.getChild()
        );

        Profile savedProfile = profileRepository.save(profile);
        return new ProfileDto(savedProfile);
    }

    @Transactional(readOnly = true)
    public List<ProfileDto> profiles(String loginId){
        List<Profile> profileList = profileRepository.findAllByMember(loginId);
        return profileList.stream().map(ProfileDto::new).toList();
    }

    public ProfileDto edit(String loginId, Long profileId, ProfileEditDto profileEditDto){
        Profile profile = profileRepository.findProfileByLoginIdAndProfileId(loginId, profileId).orElseThrow(
                () -> new NoSuchElementException("Not Found Profile")
        );
        ProfileImage editProfileImage = profileImageRepository.findById(profileEditDto.getProfileImgId()).orElseThrow(
                () -> new NoSuchElementException("Not Found ProfileImage"));
        profile.editProfile(profileEditDto.getProfileName(), editProfileImage, profileEditDto.getChild());
        return new ProfileDto(profile);
    }


    //TODO 댓글 또는 최근 기록 삭제시 지우는 로직 추가해야함
    public void delete(String loginId, Long profileId){
        Profile profile = profileRepository.findProfileByLoginIdAndProfileId(loginId, profileId).orElseThrow(
                () -> new NoSuchElementException("Not Found Profile"));
        profileRepository.delete(profile);
    }
}
