package site.hoyeonjigi.entity.content;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import site.hoyeonjigi.entity.ContentGenre;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@SuperBuilder
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

    @OneToMany(mappedBy = "content", fetch = FetchType.LAZY)
    private List<ContentGenre> contentGenres = new ArrayList<>();

    public void addViewCount(){
        if(this.viewCount >= 0){
            this.viewCount = viewCount + 1;
        }
    }
}
