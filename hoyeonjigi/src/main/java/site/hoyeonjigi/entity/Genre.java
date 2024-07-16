package site.hoyeonjigi.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Genre {

    @Id
    @Column(name = "genre_id")
    private Long id;

    private String name;

    public Genre(Long id, String name){
        this.id = id;
        this.name = name;
    }

}
