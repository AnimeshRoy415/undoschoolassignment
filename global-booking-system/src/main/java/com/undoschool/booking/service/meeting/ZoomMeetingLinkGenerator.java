package com.undoschool.booking.service.meeting;

import com.undoschool.booking.enums.MeetingProvider;
import org.springframework.stereotype.Component;

@Component
public class ZoomMeetingLinkGenerator
        implements MeetingLinkGenerator {

    @Override
    public MeetingProvider getProvider() {
        return MeetingProvider.ZOOM;
    }

    @Override
    public String generateMeetingLink(
            Long offeringId,
            Long sessionId) {

        return "https://zoom.us/j/"
                + offeringId
                + "-"
                + sessionId;
    }
}