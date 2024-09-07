package by.mainservice.modules.ws.notification;

import lombok.Builder;

@Builder
public record NotificationMessage(
        String user,
        String action
) {
}