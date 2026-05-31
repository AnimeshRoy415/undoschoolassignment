package com.undoschool.booking.service.meeting;

import com.undoschool.booking.enums.MeetingProvider;

public interface MeetingLinkGenerator {

    MeetingProvider getProvider();

    String generateMeetingLink(
            Long offeringId,
            Long sessionId
    );
}