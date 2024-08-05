package site.hoyeonjigi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.hoyeonjigi.dto.GenreDto;
import site.hoyeonjigi.service.GenreService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/genre")
public class GenreController {

    private final GenreService genreService;

    @Operation(summary = "장르 정보 조회", description = "장르 ID로 장르 정보 조회 API")
    @ApiResponse(responseCode = "200", description = "요청에 성공하였습니다", content = @Content(mediaType = "application/json"))
    @GetMapping("/{genreId}")
    public ResponseEntity<GenreDto> getGenreInfo(@PathVariable("genreId") Long genreId){
        GenreDto genreDto = genreService.genreInfo(genreId);
        return ResponseEntity.ok(genreDto);
    }
}
