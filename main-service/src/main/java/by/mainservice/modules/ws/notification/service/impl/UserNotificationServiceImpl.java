package by.mainservice.modules.ws.notification.service.impl;

import by.mainservice.common.exception.ApplicationRuntimeException;
import by.mainservice.modules.ws.notification.NotificationMessage;
import by.mainservice.modules.ws.notification.service.UserNotificationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserNotificationServiceImpl implements UserNotificationService {

    private final SimpMessagingTemplate messagingTemplate;
    private final ObjectMapper objectMapper;

    public void sendUserRequestNotification(final String login, final String message) {
        NotificationMessage notificationMessage = NotificationMessage.builder()
                .user(login)
                .action(message)
                .build();
        try {
            final var messageJson = objectMapper.writeValueAsString(notificationMessage);

            messagingTemplate.convertAndSend("/topic/user-activity", messageJson);
        } catch (final JsonProcessingException e) {
            throw new ApplicationRuntimeException("Ошибка при отправки нотификации", e);
        }
    }

}
