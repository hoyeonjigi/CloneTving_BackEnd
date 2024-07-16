package site.hoyeonjigi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary = "컨텐츠 리스트 조회", description = "컨텐츠 리스트를 조회하는 API")
    @ApiResponse(responseCode = "200", description = "요청에 성공하였습니다", content = @Content(mediaType = "application/json"))
    @Parameters({
            @Parameter(name = "type", description = "컨텐츠 타입", example = "drama"),
            @Parameter(name = "sort", description = "정렬 기준", example = "latest"),
            @Parameter(name = "page", description = "페이지 번호", example = "0"),
            @Parameter(name = "genreName", description = "장르 이름", example = "스릴러"),
            @Parameter(name = "title", description = "제목 이름", example = "쿵푸팬더")
    })
    @GetMapping
    public ResponseEntity<Page<ContentDto>> contentsByOptions(@RequestParam(value = "type",required = false) String type,
                                                     @RequestParam(value = "sort", defaultValue = "latest") String sort,
                                                     @RequestParam(value = "page", defaultValue = "0") int page,
                                                     @RequestParam(value = "genreName", required = false) String genreName,
                                                     @RequestParam(value = "title", required = false) String title){
        Page<ContentDto> contents = contentService.findContentsByOptions(type, sort,genreName,title, page);
        return ResponseEntity.ok(contents);
    }

    @Operation(summary = "컨텐츠 조회수 증가", description = "해당 컨텐츠 조회수를 증가시키는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청에 성공하였습니다", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "컨텐츠를 찾을 수 없습니다",content = @Content(mediaType = "application/json"))
    })
    @PostMapping("/{contentId}/view")
    public ResponseEntity<Void> contentAddViewCount(@PathVariable("contentId") Long contentId){
        contentService.addViewCount(contentId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "컨텐츠 조회", description = "아이디를 입력해 컨텐츠를 조회하는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청에 성공하였습니다", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "컨텐츠를 찾을 수 없습니다",content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/{contentId}")
    public ResponseEntity<ContentDto> contentsById(@PathVariable("contentId") Long contentId){
        ContentDto content = contentService.findContentById(contentId);
        return ResponseEntity.ok(content);
    }

}
