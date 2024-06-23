package site.hoyeonjigi.entity.content;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "DTYPE")
public abstract class Content {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "content_id")
    private Long id;

    private String contentTitle;

    private LocalDate releaseDate;

    private int viewCount;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String overview;

    private String poster;

    private boolean grade;

    @Column(name = "DTYPE" , insertable = false, updatable = false)
    private String dType;

}
