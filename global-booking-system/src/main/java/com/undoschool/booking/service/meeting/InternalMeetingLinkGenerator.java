package com.undoschool.booking.service.meeting;

import com.undoschool.booking.enums.MeetingProvider;
import org.springframework.stereotype.Component;

@Component
public class InternalMeetingLinkGenerator
        implements MeetingLinkGenerator {

    @Override
    public MeetingProvider getProvider() {
        return MeetingProvider.INTERNAL;
    }

    @Override
    public String generateMeetingLink(
            Long offeringId,
            Long sessionId) {

        return "https://class.undoschool.com/session/"
                + sessionId;
    }
}