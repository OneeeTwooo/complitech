package by.mainservice.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class ShutdownScheduler {

    private final ConfigurableApplicationContext applicationContext;

    @Value("${shutdown.datetime}")
    private String shutdownDateTime;

    @Scheduled(fixedRate = 600000)
    public void checkShutdownTime() {
        final var shutdownTime = LocalDateTime.parse(shutdownDateTime);
        final var currentTime = LocalDateTime.now();

        if (currentTime.isAfter(shutdownTime)) {
            System.out.println("Заверение приложения");
            SpringApplication.exit(applicationContext, () -> 0);
            System.exit(0);
        }
    }
}
