package by.mainservice.modules.auth.service;

import by.mainservice.modules.auth.api.dto.request.AuthRequestDto;
import by.mainservice.modules.auth.api.dto.response.AuthResponseDto;

public interface AuthTokenService {
    AuthResponseDto login(AuthRequestDto authRequestDto);
}
