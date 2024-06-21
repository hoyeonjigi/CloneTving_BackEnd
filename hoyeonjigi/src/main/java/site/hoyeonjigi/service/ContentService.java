package site.hoyeonjigi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import site.hoyeonjigi.dto.ContentDto;
import site.hoyeonjigi.entity.content.Content;
import site.hoyeonjigi.repository.content.ContentRepository;

@Service
@RequiredArgsConstructor
public class ContentService {
    private final ContentRepository contentRepository;

    public Page<ContentDto> findContents(String type, String sort, int page){
        Pageable pageable = PageRequest.of(page, 20);
        Page<Content> contentsByTypeAndSort = contentRepository.findContentsByTypeAndSort(type, sort, pageable);
        return contentsByTypeAndSort.map(ContentDto::new);
    }
}
