package site.hoyeonjigi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import site.hoyeonjigi.dto.profile.ProfileDto;
import site.hoyeonjigi.dto.profile.ProfileEditDto;
import site.hoyeonjigi.dto.profile.ProfileRegisterDto;
import site.hoyeonjigi.service.ProfileService;

import java.util.List;

@RestController
@RequestMapping("/profiles")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @Operation(summary = "프로필 등록", description = "유저 프로필 등록 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "요청에 성공하였습니다", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "리소스를 찾을 수 없습니다(유저 또는 프로필 이미지)", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "409", description = "이미 존재하는 프로필 이름입니다", content = @Content(mediaType = "application/json"))
    })
    @PostMapping("/register")
    public ResponseEntity<ProfileDto> register(Authentication authentication, @RequestBody @Validated ProfileRegisterDto profileRegisterDto){
        String loginId = getLoginId(authentication);
        ProfileDto savedProfile = profileService.register(loginId, profileRegisterDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProfile);
    }

    @Operation(summary = "회원의 프로필 전체 조회", description = "회원의 프로필 리스트 전체를 조회하는 API")
    @ApiResponse(responseCode = "200", description = "요청에 성공하였습니다", content = @Content(mediaType = "application/json"))
    @GetMapping
    public ResponseEntity<List<ProfileDto>> profilesByMember (Authentication authentication){
        String loginId = getLoginId(authentication);
        List<ProfileDto> profiles = profileService.profiles(loginId);
        return ResponseEntity.ok(profiles);
    }

    @Operation(summary = "회원 프로필 수정", description = "회원의 프로필을 수정하는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청에 성공하였습니다",content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "리소스를 찾을 수 없습니다(프로필 또는 프로필 이미지)")
    })
    @PatchMapping("/{profileId}")
    public ResponseEntity<ProfileDto> editProfile(@PathVariable("profileId") Long profileId, Authentication authentication,
                                                  @RequestBody @Validated ProfileEditDto profileEditDto){
        String loginId = getLoginId(authentication);
        ProfileDto editProfile = profileService.edit(loginId, profileId, profileEditDto);
        return ResponseEntity.ok(editProfile);
    }

    private String getLoginId(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userDetails.getUsername();
    }

    @Operation(summary = "회원 프로필 삭제", description = "회원의 프로필을 삭제하는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "요청에 성공하였습니다",content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "회원을 찾을 수 없습니다", content = @Content(mediaType = "application/json"))
    })
    @DeleteMapping("/{profileId}")
    public ResponseEntity<Void> deleteProfile(@PathVariable("profileId") Long profileId, Authentication authentication){
        String loginId = getLoginId(authentication);
        profileService.delete(loginId,profileId);
        return ResponseEntity.noContent().build();
    }
}
