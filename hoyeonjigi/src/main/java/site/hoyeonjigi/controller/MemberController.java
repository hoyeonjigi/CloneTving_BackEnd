package site.hoyeonjigi.controller;

import jakarta.servlet.http.HttpServletRequest;
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

    @PostMapping("/register")
    public ResponseEntity<Long> register(@RequestBody MemberRegisterDto memberRegisterDto) {

        return ResponseEntity.status(HttpStatus.CREATED).body(memberService.register(memberRegisterDto));
    }

    @PostMapping("/login")
    public ResponseEntity<JsonWebTokenDto> login(@RequestBody MemberLoginDto memberLoginDto) {

        return ResponseEntity.ok(memberService.login(memberLoginDto));
    }

    @PostMapping("/reissue")
    public ResponseEntity<JsonWebTokenDto> reissue(HttpServletRequest request) {

        return ResponseEntity.ok(memberService.reissue(request));
    }

    @GetMapping("/duplication-check")
    public ResponseEntity<Boolean> reissue(@RequestParam String loginId) {

        return ResponseEntity.ok(memberService.duplicateCheck(loginId));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Boolean> delete(@RequestParam String loginId) {

        return ResponseEntity.ok(memberService.delete(loginId));
    }

}
