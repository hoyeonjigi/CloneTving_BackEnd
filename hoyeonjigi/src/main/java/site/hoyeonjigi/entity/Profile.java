package site.hoyeonjigi.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.hoyeonjigi.entity.member.Member;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Profile {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "profile_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String profileName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_image_id")
    private ProfileImage profileImage;

    private boolean child;

    public Profile(Member member, String profileName, ProfileImage profileImage, boolean child){
        this.member = member;
        this.profileName = profileName;
        this.profileImage = profileImage;
        this.child = child;
    }

    public void editProfile(String profileName, ProfileImage profileImage, boolean child){
        this.profileName = profileName;
        this.profileImage = profileImage;
        this.child = child;
    }
}
