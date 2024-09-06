package by.mainservice.common.uuid.impl;

import by.mainservice.common.uuid.UuidGenerator;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UuidGeneratorImpl implements UuidGenerator {

    @Override
    public UUID generate() {
        return UUID.randomUUID();
    }

    @Override
    public String generateAsString() {
        return generate().toString();
    }
}
