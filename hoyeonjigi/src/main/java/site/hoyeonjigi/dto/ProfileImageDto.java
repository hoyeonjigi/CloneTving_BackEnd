package site.hoyeonjigi.dto;

import lombok.Getter;
import lombok.Setter;
import site.hoyeonjigi.entity.ProfileImage;

@Setter
@Getter
public class ProfileImageDto {

    private Long profileImageId;
    private String profileImageUrl;
    private String profileImageName;
    private String profileImageCategory;

    public ProfileImageDto(ProfileImage profileImage){
        this.profileImageId = profileImage.getId();
        this.profileImageUrl = profileImage.getProfileImageUrl();
        this.profileImageName = profileImage.getProfileImageName();
        this.profileImageCategory = profileImage.getProfileImageCategory();
    }
}
