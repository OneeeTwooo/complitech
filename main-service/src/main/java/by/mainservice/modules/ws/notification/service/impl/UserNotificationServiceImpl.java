package by.mainservice.modules.ws.notification.service.impl;

import by.mainservice.modules.ws.notification.NotificationMessage;
import by.mainservice.modules.ws.notification.service.UserNotificationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserNotificationServiceImpl implements UserNotificationService {

    private final SimpMessagingTemplate messagingTemplate;
    private final ObjectMapper objectMapper;

    public void sendUserRequestNotification(final String login, final String message) {
        log.info("Sending notification: user={}, action={}", login, message);
        final var notificationMessage = NotificationMessage.builder()
                .user(login)
                .action(message)
                .build();
        try {
            final var messageJson = objectMapper.writeValueAsString(notificationMessage);
            log.info("Notification message JSON: {}", messageJson);

            messagingTemplate.convertAndSend("/topic/user-activity", messageJson);
        } catch (final JsonProcessingException e) {
            log.error("Error sending notification", e);
        }
    }

}
