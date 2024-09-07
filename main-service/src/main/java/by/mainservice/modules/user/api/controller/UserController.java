package by.mainservice.modules.user.api.controller;

import by.mainservice.modules.auth.annatation.AdminAccess;
import by.mainservice.modules.auth.annatation.AllAccess;
import by.mainservice.modules.user.api.dto.request.UserRequestDto;
import by.mainservice.modules.user.api.dto.response.UserIdResponseDto;
import by.mainservice.modules.user.api.dto.response.UserResponseDto;
import by.mainservice.modules.user.service.UserService;
import by.mainservice.modules.ws.notification.service.UserNotificationService;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {

    private final UserNotificationService userNotificationService;
    private final UserService userService;

    @AllAccess
    @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<UserResponseDto> getAllUsers() {
        final var currentUser = userService.getCurrentUser();
        userNotificationService.sendUserRequestNotification(currentUser.getLogin(), "use request GET /users");

        return userService.getAllUsers();
    }

    @AdminAccess
    @GetMapping(path = {"/{userId}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public UserResponseDto getUserById(@PathVariable(value = "userId") final Integer userId) {
        return userService.getUserById(userId);
    }

    @AdminAccess
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public UserIdResponseDto createUser(@Valid @RequestBody final UserRequestDto userRequestDto) {
        return userService.createUser(userRequestDto);
    }

    @AdminAccess
    @PutMapping(
            path = {"/{userId}"},
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.OK)
    public UserIdResponseDto updateUser(
            @PathVariable(value = "userId") final Integer userId,
            @Valid @RequestBody final UserRequestDto userRequestDto
    ) {
        return userService.updateUser(userId, userRequestDto);
    }

    @AdminAccess
    @DeleteMapping(path = {"/{userId}"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUserById(@PathVariable(value = "userId") final Integer userId) {
        userService.deleteUserById(userId);
    }

    @AdminAccess
    @DeleteMapping(path = {"/range-delete"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUsersByRange(
            @PathParam("startId") final Integer startId,
            @PathParam("endId") final Integer endId
    ) {
        userService.deleteUsersByRange(startId, endId);
    }
}
