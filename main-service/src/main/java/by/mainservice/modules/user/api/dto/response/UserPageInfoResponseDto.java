package by.mainservice.modules.user.api.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record UserPageInfoResponseDto(
        Integer page,
        Integer total,
        Integer limit,
        List<UserResponseDto> items
) {
}
