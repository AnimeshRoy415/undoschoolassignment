package com.undoschool.booking.service.meeting;

import com.undoschool.booking.enums.MeetingProvider;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class MeetingLinkGeneratorFactory {

    private final Map<
            MeetingProvider,
            MeetingLinkGenerator> generators;

    public MeetingLinkGeneratorFactory(
            List<MeetingLinkGenerator> generators) {

        this.generators =
                generators.stream()
                        .collect(Collectors.toMap(
                                MeetingLinkGenerator::getProvider,
                                Function.identity()
                        ));
    }

    public MeetingLinkGenerator getGenerator(
            MeetingProvider provider) {

        return generators.get(provider);
    }
}