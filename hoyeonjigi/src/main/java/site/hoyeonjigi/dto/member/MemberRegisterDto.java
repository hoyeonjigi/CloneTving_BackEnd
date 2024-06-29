package site.hoyeonjigi.dto.member;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class MemberRegisterDto {

    @NotBlank(message = "loginId은 필수 입력 값입니다.")
    private String loginId;

    @NotBlank(message = "password은 필수 입력 값입니다.")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private String email;

    private boolean adultStatus;

    private boolean privacyAgreement;

    private boolean smsAgreement;

    private boolean emailAgreement;

    public MemberRegisterDto(String loginId, String password, String email, boolean adultStatus, boolean privacyAgreement, boolean smsAgreement, boolean emailAgreement) {
        this.loginId = loginId;
        this.password = password;
        this.email = email;
        this.adultStatus = adultStatus;
        this.privacyAgreement = privacyAgreement;
        this.smsAgreement = smsAgreement;
        this.emailAgreement = emailAgreement;
    }
}
