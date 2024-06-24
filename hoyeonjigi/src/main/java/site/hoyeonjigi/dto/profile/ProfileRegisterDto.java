package site.hoyeonjigi.dto.profile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileRegisterDto {
    @NotBlank(message = "profileName Not Blank")
    private String profileName;
    @NotNull(message = "profileImgId Not Null")
    private Long profileImgId;
    @NotNull(message = "child Not Null")
    private Boolean child;
}
