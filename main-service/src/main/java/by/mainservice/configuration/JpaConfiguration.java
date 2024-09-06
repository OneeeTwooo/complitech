package by.mainservice.configuration;

import by.mainservice.common.core.OffsetDatetimeProvider;
import by.mainservice.common.date.DateTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = {
                "by.mainservice.modules.user.core",
                "by.mainservice.modules.auth.core"
        })
@EnableJpaAuditing(dateTimeProviderRef = "offsetDateTimeProvider")
@Configuration
public class JpaConfiguration {

    @Bean
    public DateTimeProvider offsetDateTimeProvider(@Autowired final DateTimeService service) {
        return new OffsetDatetimeProvider(service);
    }
}
