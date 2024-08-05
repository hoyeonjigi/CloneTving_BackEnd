package site.hoyeonjigi.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import site.hoyeonjigi.entity.ContentGenre;
import site.hoyeonjigi.entity.Genre;
import site.hoyeonjigi.entity.content.Content;
import site.hoyeonjigi.entity.content.Drama;
import site.hoyeonjigi.entity.content.Movie;
import site.hoyeonjigi.repository.content.ContentRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles(value = {"local"})
@Transactional
@Slf4j
public class ContentRepositoryTest {

    @Autowired
    private ContentRepository contentRepository;
    @Autowired
    private GenreRepository genreRepository;
    @Autowired
    private ContentGenreRepository contentGenreRepository;

    Content drama;
    Content movie;
    Genre thriller;
    Genre action;
    ContentGenre contentGenre;
    @BeforeEach
    public void setData() {
        drama = Drama.builder()
                .contentTitle("DramaTest")
                .dType("drama")
                .contentGenres(new ArrayList<>())
                .poster(null)
                .grade(true)
                .overview(null)
                .releaseDate(LocalDate.now())
                .viewCount(1)
                .build();

        movie = Movie.builder()
                .contentTitle("MovieTest")
                .dType("movie")
                .contentGenres(new ArrayList<>())
                .poster(null)
                .grade(true)
                .overview(null)
                .releaseDate(LocalDate.now())
                .viewCount(0)
                .build();

        thriller = new Genre(1L,"스릴러");
        action = new Genre(2L, "액션");

        //장르 저장
        genreRepository.save(thriller);
        genreRepository.save(action);

        //콘텐츠 저장(스릴러 드라마)
        contentGenre = new ContentGenre(drama,thriller);
        contentGenreRepository.save(contentGenre);
        drama.getContentGenres().add(contentGenre);
        contentRepository.save(drama);

        //콘텐츠 저장(액션 영화)
        contentGenre = new ContentGenre(movie, action);
        contentGenreRepository.save(contentGenre);
        movie.getContentGenres().add(contentGenre);
        contentRepository.save(movie);
    }

    @Test
    @DisplayName("인기순 콘텐츠 조회")
    void OrderByPopular(){
        Pageable pageable =PageRequest.of(0,20);
        Page<Content> popular = contentRepository.findContentsByTypeAndSortOrGenre(null, "popular", null, null, pageable);
        assertThat(popular.getTotalElements()).isEqualTo(2);
        assertThat(popular.getContent().get(0).getContentTitle()).isEqualTo("DramaTest");
    }

    @Test
    @DisplayName("최신순 콘텐츠 조회")
    void OrderByLatest(){
        Pageable pageable =PageRequest.of(0,20);
        Page<Content> latest = contentRepository.findContentsByTypeAndSortOrGenre(null, "latest", null, null, pageable);
        assertThat(latest.getTotalElements()).isEqualTo(2);
        assertThat(latest.getContent().get(0).getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("타입별 콘텐츠 조회")
    void findByType(){
        Pageable pageable =PageRequest.of(0,20);
        Page<Content> drama = contentRepository.findContentsByTypeAndSortOrGenre("drama", "latest", null, null, pageable);
        assertThat(drama.getTotalElements()).isEqualTo(1);
        assertThat(drama.getContent().get(0).getContentTitle()).isEqualTo("DramaTest");

        Page<Content> movie = contentRepository.findContentsByTypeAndSortOrGenre("movie", "latest", null, null, pageable);
        assertThat(movie.getTotalElements()).isEqualTo(1);
        assertThat(movie.getContent().get(0).getContentTitle()).isEqualTo("MovieTest");
    }

    @Test
    @DisplayName("장르별 콘텐츠 조회")
    void findByGenre(){
        Pageable pageable =PageRequest.of(0,20);
        Page<Content> action = contentRepository.findContentsByTypeAndSortOrGenre(null, "latest", "액션", null, pageable);
        assertThat(action.getTotalElements()).isEqualTo(1);
        assertThat(action.getContent().get(0).getContentTitle()).isEqualTo("MovieTest");

        List<ContentGenre> actionGenres = action.getContent().get(0).getContentGenres();
        assertThat(actionGenres).anyMatch(cg -> "액션".equals(cg.getGenre().getName()));

        Page<Content> thriller = contentRepository.findContentsByTypeAndSortOrGenre(null, "latest", "스릴러", null, pageable);
        assertThat(thriller.getTotalElements()).isEqualTo(1);
        assertThat(thriller.getContent().get(0).getContentTitle()).isEqualTo("DramaTest");

        List<ContentGenre> thrillerGenres = thriller.getContent().get(0).getContentGenres();
        assertThat(thrillerGenres).anyMatch(cg -> "스릴러".equals(cg.getGenre().getName()));
    }

    @Test
    @DisplayName("제목별 콘텐츠 조회")
    void findByTitle(){
        Pageable pageable =PageRequest.of(0,20);
        Page<Content> movie = contentRepository.findContentsByTypeAndSortOrGenre(null, "latest", null, "Movie", pageable);
        assertThat(movie.getTotalElements()).isEqualTo(1);
        assertThat(movie.getContent().get(0).getContentTitle()).isEqualTo("MovieTest");

        Page<Content> drama = contentRepository.findContentsByTypeAndSortOrGenre(null, "latest", null, "Drama", pageable);
        assertThat(drama.getTotalElements()).isEqualTo(1);
        assertThat(drama.getContent().get(0).getContentTitle()).isEqualTo("DramaTest");
    }

    @Test
    @DisplayName("스릴러 드라마 조회")
    void findByMultiOption(){
        Pageable pageable =PageRequest.of(0,20);
        Page<Content> thrillerDrama = contentRepository.findContentsByTypeAndSortOrGenre("drama", "latest", "스릴러", "Drama", pageable);
        assertThat(thrillerDrama.getTotalElements()).isEqualTo(1);
        assertThat(thrillerDrama.getContent().get(0).getContentTitle()).isEqualTo("DramaTest");
    }
}
