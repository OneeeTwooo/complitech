package by.mainservice.common.core;

import by.mainservice.common.date.DateTimeService;
import lombok.AllArgsConstructor;
import org.springframework.data.auditing.DateTimeProvider;

import java.time.temporal.TemporalAccessor;
import java.util.Optional;

@AllArgsConstructor
public class OffsetDatetimeProvider implements DateTimeProvider {

    private final DateTimeService dateTimeService;

    @SuppressWarnings("NullableProblems")
    @Override
    public Optional<TemporalAccessor> getNow() {
        return Optional.of(dateTimeService.nowOffsetDateTime());
    }
}
