package site.hoyeonjigi.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.hoyeonjigi.dto.ContentDto;
import site.hoyeonjigi.service.ContentService;

@RestController
@RequestMapping("/contents")
@RequiredArgsConstructor
@Slf4j
public class ContentController {
    private final ContentService contentService;
    //인기순, 최신순, 장르별 데이터 조회 파라미터로 옵션 추가 반환(Page) 파라미터 (latest, popular, genre, title)
    @GetMapping
    public ResponseEntity<Page<ContentDto>> byOptions(@RequestParam(value = "type",required = false) String type,
                                                      @RequestParam(value = "sort", defaultValue = "latest") String sort,
                                                      @RequestParam(value = "page", defaultValue = "0") int page,
                                                      @RequestParam(value = "genreName", required = false) String genreName,
                                                      @RequestParam(value = "title", required = false) String title){
        Page<ContentDto> contents = contentService.findContentsByOptions(type, sort,genreName,title, page);
        return ResponseEntity.ok(contents);
    }

    //컨텐츠 조회수 증가
    @GetMapping("/{contentId}/view")
    public ResponseEntity<Void> contentAddViewCount(@PathVariable("contentId") Long contentId){
        contentService.addViewCount(contentId);
        return ResponseEntity.noContent().build();
    }

    //컨텐츠 아이디로 조회
    @GetMapping("/{contentId}")
    public ResponseEntity<ContentDto> contentsById(@PathVariable("contentId") Long contentId){
        ContentDto content = contentService.findContentById(contentId);
        return ResponseEntity.ok(content);
    }

}
