package site.hoyeonjigi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.hoyeonjigi.dto.GenreDto;
import site.hoyeonjigi.entity.Genre;
import site.hoyeonjigi.repository.GenreRepository;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GenreService {

    private final GenreRepository genreRepository;
    public GenreDto genreInfo(Long genreId){
        Genre genre = genreRepository.findById(genreId).orElseThrow(() -> new NoSuchElementException("Not Found Genre"));
        return new GenreDto(genre);
    }
}
