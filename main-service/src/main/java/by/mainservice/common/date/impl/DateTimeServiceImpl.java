package by.mainservice.common.date.impl;

import by.mainservice.common.date.DateTimeService;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
public class DateTimeServiceImpl implements DateTimeService {

    @Override
    public OffsetDateTime nowOffsetDateTime() {
        return OffsetDateTime.now();
    }

}
