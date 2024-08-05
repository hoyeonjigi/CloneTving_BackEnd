package site.hoyeonjigi.dto.member;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class MemberLoginDto {

    @NotBlank(message = "loginId은 필수 입력 값입니다.")
    private String loginId;

    @NotBlank(message = "password은 필수 입력 값입니다.")
    private String password;

    public MemberLoginDto(String loginId, String password) {
        this.loginId = loginId;
        this.password = password;
    }
}
