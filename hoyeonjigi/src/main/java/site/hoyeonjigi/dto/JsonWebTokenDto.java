package site.hoyeonjigi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class JsonWebTokenDto {

    private String accessToken;

    private String refreshToken;
}
