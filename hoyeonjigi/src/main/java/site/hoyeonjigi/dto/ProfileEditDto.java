package site.hoyeonjigi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProfileEditDto {
    @NotBlank(message = "profileName Not Blank")
    private String profileName;
    @NotNull(message = "profileImgId Not Null")
    private Long profileImgId;
    @NotNull(message = "child Not Null")
    private Boolean child;
}
