package site.hoyeonjigi.repository.content;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import site.hoyeonjigi.entity.content.Content;

import java.util.Optional;

public interface ContentRepository extends JpaRepository<Content, Long> , ContentCustomRepository{

    @Query("select c from Content c join fetch c.contentGenres where c.id =:id")
    Optional<Content> findContentAndContentGenreById(@Param("id") Long id);
}
