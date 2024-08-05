package site.hoyeonjigi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.hoyeonjigi.entity.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {
}
