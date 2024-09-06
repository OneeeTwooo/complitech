package by.mainservice.modules.auth.api.dto.response;

import lombok.Builder;

@Builder
public record AuthResponseDto(
        String accessToken,
        String refreshToken
) {
}
