package site.hoyeonjigi.repository.content;

import org.springframework.data.jpa.repository.JpaRepository;
import site.hoyeonjigi.entity.content.Content;

public interface ContentRepository extends JpaRepository<Content, Long> , ContentCustomRepository{
}
