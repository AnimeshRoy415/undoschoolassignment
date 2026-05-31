package com.undoschool.booking.service.strategy;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class SessionGenerationFactory {

    private final Map<String, SessionGenerationStrategy>
            strategyMap = new HashMap<>();

    public SessionGenerationFactory(
            List<SessionGenerationStrategy> strategies) {

        strategies.forEach(
                strategy -> strategyMap.put(
                        strategy.getScheduleCode(),
                        strategy
                )
        );
    }

    public SessionGenerationStrategy getStrategy(
            String scheduleCode) {

        SessionGenerationStrategy strategy =
                strategyMap.get(
                        scheduleCode.toUpperCase()
                );

        if (strategy == null) {
            throw new IllegalArgumentException(
                    "Invalid schedule type: "
                            + scheduleCode
            );
        }

        return strategy;
    }
}