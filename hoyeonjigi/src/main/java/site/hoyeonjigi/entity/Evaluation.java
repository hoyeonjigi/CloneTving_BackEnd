package site.hoyeonjigi.entity;


import jakarta.persistence.*;
import lombok.*;
import site.hoyeonjigi.entity.content.Content;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Evaluation {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "evaluation_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id")
    private Profile profile;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "content_id")
    private Content content;

    private String review;

    private LocalDateTime ratingTime;

    private Long goodCount;

    private Long badCount;

    private Double rating;

    public void editReview(String review, Double rating) {
        this.review = review;
        this.rating = rating;
    }

    public void increaseGoodCount() {
        this.goodCount = goodCount + 1;
    }

    public void increaseBadCount() {
        this.badCount = badCount + 1;;
    }

    public void decreaseGoodCount() {
        this.goodCount = goodCount - 1;
    }

    public void decreaseBadCount() {
        this.badCount = badCount - 1;;
    }
}
