package by.mainservice.modules.auth.api.controller;

import by.mainservice.modules.auth.annatation.AllAccess;
import by.mainservice.modules.auth.api.dto.request.AuthRequestDto;
import by.mainservice.modules.auth.api.dto.response.AuthResponseDto;
import by.mainservice.modules.auth.service.AuthTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth/")
@RequiredArgsConstructor
public class AuthController {

    private final AuthTokenService authTokenService;

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public AuthResponseDto login(@RequestBody final AuthRequestDto authRequestDto) {
        return authTokenService.login(authRequestDto);
    }

    @AllAccess
    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void logout() {
        authTokenService.logout();
    }
}