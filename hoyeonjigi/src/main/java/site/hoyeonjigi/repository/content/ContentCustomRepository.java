package site.hoyeonjigi.repository.content;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import site.hoyeonjigi.entity.content.Content;

public interface ContentCustomRepository {
    Page<Content> findContentsByTypeAndSortOrGenre(String type, String sort, String genreName, String title, Pageable pageable);
}
