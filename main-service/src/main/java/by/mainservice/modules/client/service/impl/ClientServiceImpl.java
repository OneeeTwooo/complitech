package by.mainservice.modules.client.service.impl;

import by.mainservice.common.exception.ApplicationRuntimeException;
import by.mainservice.modules.auth.api.dto.request.AuthRequestDto;
import by.mainservice.modules.auth.api.dto.response.AuthResponseDto;
import by.mainservice.modules.client.service.ClientService;
import by.mainservice.modules.user.api.dto.request.UserRequestDto;
import by.mainservice.modules.user.api.dto.response.UserIdResponseDto;
import by.mainservice.modules.user.core.entity.GenderType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.HttpStatus.ACCEPTED;


@Service
public class ClientServiceImpl implements ClientService {

    private static final String CREATE_USER_URL = "http://localhost:8080/api/auth/login";
    private static final String LOGIN_URL = "http://localhost:8080/api/auth/login";
    private static final String BEARER = "Bearer ";

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final HttpClient client = HttpClient.newHttpClient();

    @Override
    public UserIdResponseDto loginAndCreateUser() {
        final var token = login();

        return createUser(token);
    }

    private String login() {
        try {
            final var request = prepareLoginRequest();

            final var response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (ACCEPTED.value() == response.statusCode()) {
                final var authResponse = objectMapper.readValue(response.body(), AuthResponseDto.class);
                return authResponse.accessToken();
            } else {
                throw new ApplicationRuntimeException("Login failed. HTTP status: " + response.statusCode());
            }
        } catch (Exception e) {
            throw new ApplicationRuntimeException("Error during login", e);
        }
    }

    private UserIdResponseDto createUser(final String token) {
        try {
            final var request = prepareCreateUserRequest(token);

            final var response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == HttpStatus.CREATED.value()) {
                return objectMapper.readValue(response.body(), UserIdResponseDto.class);
            } else {
                throw new ApplicationRuntimeException("Failed to create user. HTTP status: " + response.statusCode());
            }
        } catch (Exception e) {
            throw new ApplicationRuntimeException("Error during HTTP request", e);
        }
    }

    private HttpRequest prepareLoginRequest() throws JsonProcessingException {
        final var authRequestDto = AuthRequestDto.builder()
                .username("admin")
                .password("admin")
                .build();

        final var requestBody = objectMapper.writeValueAsString(authRequestDto);

        return HttpRequest.newBuilder()
                .uri(URI.create(LOGIN_URL))
                .timeout(Duration.ofSeconds(10))
                .header(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
    }

    private HttpRequest prepareCreateUserRequest(final String token) throws JsonProcessingException {
        final var userRequestDto = UserRequestDto.builder()
                .login("testUser")
                .password("password!111")
                .fullName("test user")
                .genderType(GenderType.UNDEFINED)
                .build();

        final var requestBody = objectMapper.writeValueAsString(userRequestDto);

        return HttpRequest.newBuilder()
                .uri(URI.create(CREATE_USER_URL))
                .timeout(Duration.ofSeconds(10))
                .header(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(AUTHORIZATION, BEARER + token)
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
    }

}
