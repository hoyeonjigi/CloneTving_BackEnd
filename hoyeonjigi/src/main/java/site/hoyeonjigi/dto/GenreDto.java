package site.hoyeonjigi.dto;

import lombok.Getter;
import lombok.Setter;
import site.hoyeonjigi.entity.Genre;

@Getter
@Setter
public class GenreDto {
    private Long genreId;
    private String genreName;

    public GenreDto(Genre genre){
        this.genreId = genre.getId();
        this.genreName = genre.getName();
    }
}
