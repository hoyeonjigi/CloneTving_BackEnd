package site.hoyeonjigi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.hoyeonjigi.dto.ProfileImageDto;
import site.hoyeonjigi.service.ProfileImageService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/profileImages")
public class ProfileImageController {

    private final ProfileImageService profileImageService;
    @GetMapping
    public ResponseEntity<List<ProfileImageDto>> profileImageList(){
        List<ProfileImageDto> profileImageDtos = profileImageService.profileImages();
        return ResponseEntity.ok(profileImageDtos);
    }
}
