package by.mainservice.modules.ws.notification.service;

public interface UserNotificationService {

    void sendUserRequestNotification(final String login, final String message);
}
