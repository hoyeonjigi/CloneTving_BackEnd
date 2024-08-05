package site.hoyeonjigi.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.hoyeonjigi.dto.ContentDto;
import site.hoyeonjigi.entity.content.Content;
import site.hoyeonjigi.repository.content.ContentRepository;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class ContentService {
    private final ContentRepository contentRepository;

    public Page<ContentDto> findContentsByOptions(String type, String sort, String genreName, String title, int page){
        Pageable pageable = PageRequest.of(page, 20);
        Page<Content> contentsByTypeAndSort = contentRepository.findContentsByTypeAndSortOrGenre(type, sort,genreName, title, pageable);
        return contentsByTypeAndSort.map(ContentDto::new);
    }

    @Transactional
    public void addViewCount(Long contentId){
        Content content = contentRepository.findById(contentId).orElseThrow(() ->
                new NoSuchElementException("Not Found Content"));
        content.addViewCount();
    }

    public ContentDto findContentById(Long contentId){
        Content findContent = contentRepository.findContentAndContentGenreById(contentId).orElseThrow(() ->
                new NoSuchElementException("Not Found Content"));
        return new ContentDto(findContent);
    }
}
