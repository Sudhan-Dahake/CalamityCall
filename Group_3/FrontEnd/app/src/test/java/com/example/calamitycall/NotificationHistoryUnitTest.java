package com.example.calamitycall;

import com.example.calamitycall.models.NotificationHistory.NotificationHistoryRequest;
import com.example.calamitycall.models.NotificationHistory.NotificationHistoryResponse;
import com.example.calamitycall.models.NotificationHistory.NotificationResponse;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class NotificationHistoryUnitTest {

    /*** Happy Path Test for NotificationHistoryRequest class ***/
    @Test
    public void testNotificationHistoryRequest() {
        // Test the constructor
        NotificationHistoryRequest request = new NotificationHistoryRequest("Last 24 hours");
        assertEquals("Last 24 hours", request.getTimeFrame());

        // Test the setter
        request.setTimeFrame("Last 7 days");
        assertEquals("Last 7 days", request.getTimeFrame());
    }

    /*** Sad Path Tests for NotificationHistoryRequest class ***/
    @Test
    public void testNotificationHistoryRequestWithNullTimeFrame() {
        // Create an instance with null timeFrame
        NotificationHistoryRequest request = new NotificationHistoryRequest(null);

        // Verify the timeFrame is null
        assertNull("TimeFrame should be null", request.getTimeFrame());

        // Attempt to update the timeFrame to null
        request.setTimeFrame(null);
        assertNull("TimeFrame should remain null after setting null", request.getTimeFrame());
    }

    @Test
    public void testNotificationHistoryRequestWithEmptyTimeFrame() {
        // Create an instance with an empty string timeFrame
        NotificationHistoryRequest request = new NotificationHistoryRequest("");

        // Verify the timeFrame is an empty string
        assertEquals("TimeFrame should be an empty string", "", request.getTimeFrame());

        // Update the timeFrame to another empty string
        request.setTimeFrame("");
        assertEquals("TimeFrame should remain an empty string after setting empty", "", request.getTimeFrame());
    }

    @Test
    public void testNotificationHistoryRequestWithInvalidTimeFrame() {
        // Create an instance with an invalid timeFrame
        String invalidTimeFrame = "InvalidTimeFrameFormat";
        NotificationHistoryRequest request = new NotificationHistoryRequest(invalidTimeFrame);

        // Verify the timeFrame is set to the invalid value
        assertEquals("TimeFrame should match the invalid input", invalidTimeFrame, request.getTimeFrame());

        // Attempt to set another invalid timeFrame
        String anotherInvalidTimeFrame = "12345";
        request.setTimeFrame(anotherInvalidTimeFrame);
        assertEquals("TimeFrame should match the new invalid input", anotherInvalidTimeFrame, request.getTimeFrame());
    }
}
