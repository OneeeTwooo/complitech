package by.mainservice.modules.client.service;

import by.mainservice.modules.user.api.dto.response.UserIdResponseDto;

public interface ClientService {

    UserIdResponseDto loginAndCreateUser();
}
