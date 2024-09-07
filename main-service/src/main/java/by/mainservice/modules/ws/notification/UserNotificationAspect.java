package by.mainservice.modules.ws.notification;

import by.mainservice.modules.user.service.UserService;
import by.mainservice.modules.ws.notification.service.UserNotificationService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class UserNotificationAspect {

    private final UserNotificationService userNotificationService;
    private final UserService userService;

    @Pointcut("execution(public * by.mainservice.modules.user.api.controller.UserController.*(..))")
    public void controllerMethods() {
    }

    @AfterReturning(pointcut = "controllerMethods()", returning = "result")
    public void sendNotificationAfterMethodExecution(final JoinPoint joinPoint, final Object result) {
        final var methodName = joinPoint.getSignature().getName();

        final var currentUser = userService.getCurrentUser();

        userNotificationService.sendUserRequestNotification(currentUser.getLogin(), "use request " + methodName);
    }
}
