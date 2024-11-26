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

    @Test
    public void testNotificationHistoryRequest() {
        // Test the constructor
        NotificationHistoryRequest request = new NotificationHistoryRequest("Last 24 hours");
        assertEquals("Last 24 hours", request.getTimeFrame());

        // Test the setter
        request.setTimeFrame("Last 7 days");
        assertEquals("Last 7 days", request.getTimeFrame());
    }
}
