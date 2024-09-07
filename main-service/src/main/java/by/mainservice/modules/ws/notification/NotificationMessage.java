package by.mainservice.modules.ws.notification;

import lombok.Builder;

@Builder
public class NotificationMessage {
    private final String user;
    private final String action;
    private final String className;
}