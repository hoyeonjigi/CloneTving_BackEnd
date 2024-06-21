package site.hoyeonjigi.entity;

import jakarta.persistence.*;
import lombok.*;
import site.hoyeonjigi.entity.content.Content;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecentVisit {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recent_visit_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id")
    private Profile profile;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "content_id")
    private Content content;

    private LocalDateTime visitTime;

    private boolean interest;

}
