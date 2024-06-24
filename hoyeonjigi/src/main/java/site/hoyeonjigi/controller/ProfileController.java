package site.hoyeonjigi.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import site.hoyeonjigi.dto.ProfileDto;
import site.hoyeonjigi.dto.ProfileEditDto;
import site.hoyeonjigi.dto.ProfileRegisterDto;
import site.hoyeonjigi.service.ProfileService;

import java.util.List;

@RestController
@RequestMapping("/profiles")
@RequiredArgsConstructor
@Slf4j
public class ProfileController {

    private final ProfileService profileService;
    @PostMapping("/register")
    public ResponseEntity<ProfileDto> register(Authentication authentication, @RequestBody @Validated ProfileRegisterDto profileRegisterDto){
        String loginId = getLoginId(authentication);
        ProfileDto savedProfile = profileService.register(loginId, profileRegisterDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProfile);
    }

    @GetMapping
    public ResponseEntity<List<ProfileDto>> profilesByMember (Authentication authentication){
        String loginId = getLoginId(authentication);
        List<ProfileDto> profiles = profileService.profiles(loginId);
        return ResponseEntity.ok(profiles);
    }

    @PatchMapping("/{profileId}")
    public ResponseEntity<ProfileDto> editProfile(@PathVariable("profileId") Long profileId, Authentication authentication,
                                                  @RequestBody @Validated ProfileEditDto profileEditDto){
        String loginId = getLoginId(authentication);

        return null;
    }

    private String getLoginId(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userDetails.getUsername();
    }
}
