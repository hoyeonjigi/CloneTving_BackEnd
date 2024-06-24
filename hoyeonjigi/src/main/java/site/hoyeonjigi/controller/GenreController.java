package site.hoyeonjigi.controller;

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
    @GetMapping("/{genreId}")
    public ResponseEntity<GenreDto> getGenreInfo(@PathVariable("genreId") Long genreId){
        GenreDto genreDto = genreService.genreInfo(genreId);
        return ResponseEntity.ok(genreDto);
    }
}
