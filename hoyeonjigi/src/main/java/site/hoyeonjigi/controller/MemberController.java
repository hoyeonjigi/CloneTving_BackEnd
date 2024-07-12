package site.hoyeonjigi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.hoyeonjigi.dto.JsonWebTokenDto;
import site.hoyeonjigi.dto.member.MemberLoginDto;
import site.hoyeonjigi.dto.member.MemberRegisterDto;
import site.hoyeonjigi.service.MemberService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "멤버 회원가입", description = "유저측에서 회원가입할 때 사용하는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "요청에 성공하였습니다.", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "409", description = "이미 존재하는 아이디입니다.", content = @Content(mediaType = "application/json"))
    })
    @Parameters({
            @Parameter(name = "loginId", description = "영문 또는 영문, 숫자 조합 6-12자리", example = "test1234"),
            @Parameter(name = "password", description = "영문, 숫자, 특수문자(~!@#$%^&*) 조합 8~15자리", example = "test1234!"),
            @Parameter(name = "email", description = "이메일", example = "test1234@gmail.com"),
            @Parameter(name = "adultStatus", description = "성인 여부", example = "true"),
            @Parameter(name = "privacyAgreement", description = "서비스 이용 약관 동의 여부", example = "true"),
            @Parameter(name = "snsAgreement", description = "마케팅 정보 SMS 수신동의 여부", example = "true"),
            @Parameter(name = "emailAgreement", description = "마케팅 정보 이메일 수신동의 여부", example = "true")
    })
    @PostMapping("/register")
    public ResponseEntity<Long> register(@RequestBody @Valid MemberRegisterDto memberRegisterDto) {

        return ResponseEntity.status(HttpStatus.CREATED).body(memberService.register(memberRegisterDto));
    }

    @Operation(summary = "멤버 로그인", description = "유저측에서 로그인할 때 사용하는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "403", description = "비밀번호가 일치하지 않습니다.", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 ID입니다.", content = @Content(mediaType = "application/json"))
    })
    @Parameters({
            @Parameter(name = "loginId", description = "회원가입한 계정의 아이디 입력", example = "test1234"),
            @Parameter(name = "password", description = "회원가입한 계정의 비밀번호 입력", example = "test1234!")
    })
    @PostMapping("/login")
    public ResponseEntity<JsonWebTokenDto> login(@RequestBody @Valid MemberLoginDto memberLoginDto) {

        return ResponseEntity.ok(memberService.login(memberLoginDto));
    }

    @Operation(summary = "토큰 재발급", description = "유저측에서 엑세스토큰이 만료되었을 때, 토큰을 재발급받는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "403", description = "토큰정보가 유효하지 않습니다.", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 ID입니다.", content = @Content(mediaType = "application/json"))
    })
    @PostMapping("/reissue")
    public ResponseEntity<JsonWebTokenDto> reissue(HttpServletRequest request) {

        String refreshToken = request.getHeader("Refresh-Token");

        return ResponseEntity.ok(memberService.reissue(refreshToken));
    }

    @Operation(summary = "아이디 중복 체크", description = "유저측에서 회원가입 시, 사용할 아이디의 중복을 체크하는 API")
    @ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.", content = @Content(mediaType = "application/json"))
    @Parameter(name = "loginId", description = "회원가입할 아이디 입력", example = "test1234")
    @GetMapping("/duplication-check")
    public ResponseEntity<Boolean> duplicateCheck(@RequestParam String loginId) {

        return ResponseEntity.ok(memberService.duplicateCheck(loginId));
    }

    @Operation(summary = "멤버 삭제", description = "유저측에서 회원 탈퇴 시, 아이디를 삭제하는 API")
    @ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.", content = @Content(mediaType = "application/json"))
    @Parameter(name = "loginId", description = "회원가입한 아이디 입력", example = "test1234")
    @DeleteMapping("/delete")
    public ResponseEntity<Long> delete(@RequestParam String loginId) {

        return ResponseEntity.ok(memberService.delete(loginId));
    }

}
