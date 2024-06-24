package site.hoyeonjigi.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.hoyeonjigi.dto.JsonWebTokenDto;
import site.hoyeonjigi.dto.MemberLoginDto;
import site.hoyeonjigi.dto.MemberRegisterDto;
import site.hoyeonjigi.service.MemberService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/register")
    public Long register(@RequestBody MemberRegisterDto memberRegisterDto) {

        return memberService.register(memberRegisterDto);
    }

    @PostMapping("/login")
    public JsonWebTokenDto login(@RequestBody MemberLoginDto memberLoginDto) {

        return memberService.login(memberLoginDto);
    }

    @PostMapping("/reissue")
    public JsonWebTokenDto reissue(HttpServletRequest request) {

        return memberService.reissue(request);
    }

    @GetMapping("/duplication-check")
    public boolean reissue(@RequestParam String loginId) {

        return memberService.duplicateCheck(loginId);
    }

}
