package site.hoyeonjigi.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProfileImage {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "profile_image_id")
    private Long id;

    private String profileImageName;

    private String profileImageUrl;

    private String profileImageCategory;

    public ProfileImage(String profileImageName, String profileImageUrl, String profileImageCategory){
        this.profileImageName = profileImageName;
        this.profileImageUrl = profileImageUrl;
        this.profileImageCategory = profileImageCategory;
    }
}
