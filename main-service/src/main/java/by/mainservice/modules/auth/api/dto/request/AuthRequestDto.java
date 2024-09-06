package by.mainservice.modules.auth.api.dto.request;

import lombok.Builder;

@Builder
public record AuthRequestDto(
        String username,
        String password
) {
}
