package by.mainservice.modules.client.api.controller;

import by.mainservice.modules.client.service.ClientService;
import by.mainservice.modules.user.api.dto.response.UserIdResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/client/")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public UserIdResponseDto loginAndCreateUser() {
        return clientService.loginAndCreateUser();
    }
}
