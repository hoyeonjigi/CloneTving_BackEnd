package site.hoyeonjigi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.hoyeonjigi.dto.profile.ProfileImageDto;
import site.hoyeonjigi.service.ProfileImageService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/profileImages")
public class ProfileImageController {

    private final ProfileImageService profileImageService;

    @Operation(summary = "프로필 이미지 URL 전체 조회", description = "모든 프로필 이미지 URL 리스트로 반환")
    @ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.", content = @Content(mediaType = "application/json"))
    @GetMapping
    public ResponseEntity<List<ProfileImageDto>> profileImageList(){
        List<ProfileImageDto> profileImageDtos = profileImageService.profileImages();
        return ResponseEntity.ok(profileImageDtos);
    }
}
