package site.hoyeonjigi.dto;

import lombok.Getter;
import lombok.Setter;
import site.hoyeonjigi.entity.content.Content;

import java.time.LocalDate;

@Getter
@Setter
public class ContentDto {
    private Long contentId;
    private String title;
    private String poster;
    private String overview;
    private int viewCount;
    private LocalDate releaseDate;
    private boolean grade;

    public ContentDto(Content content){
        this.contentId = content.getId();
        this.title = content.getContentTitle();
        this.poster = content.getPoster();
        this.overview = content.getOverview();
        this.viewCount = content.getViewCount();
        this.releaseDate = content.getReleaseDate();
        this.grade = content.isGrade();
    }
}
