package com.undoschool.booking.service.meeting;

import com.undoschool.booking.enums.MeetingProvider;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class GoogleMeetLinkGenerator
        implements MeetingLinkGenerator {

    @Override
    public MeetingProvider getProvider() {
        return MeetingProvider.GOOGLE_MEET;
    }

    @Override
    public String generateMeetingLink(
            Long offeringId,
            Long sessionId) {

        return "https://meet.google.com/"
                + UUID.randomUUID()
                .toString()
                .substring(0, 10);
    }
}