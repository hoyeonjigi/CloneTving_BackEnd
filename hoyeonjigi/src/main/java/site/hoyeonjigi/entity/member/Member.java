package site.hoyeonjigi.entity.member;

import jakarta.persistence.*;
import lombok.*;
import site.hoyeonjigi.entity.Profile;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String loginId;

    private String password;

    @Enumerated(EnumType.STRING)
    private RoleType role;

    private String email;

    private boolean adultStatus;

    private boolean privacyAgreement;

    private boolean smsAgreement;

    private boolean emailAgreement;

    @OneToMany(mappedBy = "member")
    private List<Profile> profiles = new ArrayList<>();

}
