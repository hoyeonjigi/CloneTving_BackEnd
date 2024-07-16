package site.hoyeonjigi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.hoyeonjigi.entity.ContentGenre;

public interface ContentGenreRepository extends JpaRepository<ContentGenre, Long> {
}
