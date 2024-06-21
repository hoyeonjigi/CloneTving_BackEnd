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
    //인기순, 최신순 데이터 조회 반환(Page)
    @GetMapping("/{type}")
    public ResponseEntity<Page<ContentDto>> content(@PathVariable("type") String type,
                                                    @RequestParam(value = "sort") String sort,
                                                    @RequestParam(value = "page", defaultValue = "0") int page){
        Page<ContentDto> contents = contentService.findContents(type, sort, page);
        return ResponseEntity.ok(contents);
    }
}
