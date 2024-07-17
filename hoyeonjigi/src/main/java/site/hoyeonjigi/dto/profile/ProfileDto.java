package site.hoyeonjigi.dto.profile;

import lombok.Getter;
import lombok.Setter;
import site.hoyeonjigi.entity.Profile;

@Getter
@Setter
public class ProfileDto {

    private Long profileId;
    private String profileName;
    private String profileImageUrl;
    private String profileImageName;
    private boolean child;

    public ProfileDto(Profile profile){
        this.profileId = profile.getId();
        this.profileName = profile.getProfileName();
        this.profileImageUrl = profile.getProfileImage().getProfileImageUrl();
        this.profileImageName = profile.getProfileImage().getProfileImageName();
        this.child = profile.isChild();
    }

    public ProfileDto(Long id, String name, String imgUrl, String imgName, boolean child){
        this.profileId = id;
        this.profileName = name;
        this.profileImageUrl = imgUrl;
        this.profileImageName = imgName;
        this.child = child;
    }

    public ProfileDto(){
    }
}
