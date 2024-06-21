package site.hoyeonjigi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class MemberRegisterDto {

    private String loginId;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private String email;

    private boolean adultStatus;

    private boolean privacyAgreement;

    private boolean smsAgreement;

    private boolean emailAgreement;
}
