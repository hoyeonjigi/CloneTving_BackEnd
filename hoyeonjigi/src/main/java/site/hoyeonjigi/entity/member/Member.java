package site.hoyeonjigi.entity.member;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name="member",
        uniqueConstraints= {
                @UniqueConstraint(
                        name = "unique_login_id",
                        columnNames = {
                                "login_id"
                        }
                )
        }
)
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

}
