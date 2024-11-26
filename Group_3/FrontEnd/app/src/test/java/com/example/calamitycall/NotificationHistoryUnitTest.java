package com.example.calamitycall;

import com.example.calamitycall.models.NotificationHistory.NotificationHistoryRequest;
import com.example.calamitycall.models.NotificationHistory.NotificationHistoryResponse;
import com.example.calamitycall.models.NotificationHistory.NotificationResponse;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

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

    /*** Happy Path Test for NotificationHistoryResponse class ***/
    @Test
    public void testNotificationValidNotifications() {
        // Create a valid list of NotificationResponse objects
        NotificationResponse notification1 = new NotificationResponse(
                "System1", -79.3832, 43.6532, "Toronto", "Flood", 3, "2024-11-25");
        NotificationResponse notification2 = new NotificationResponse(
                "System2", -123.1216, 49.2827, "Vancouver", "Earthquake", 5, "2024-11-25");

        List<NotificationResponse> notifications = Arrays.asList(notification1, notification2);

        // Create an instance and set notifications
        NotificationHistoryResponse response = new NotificationHistoryResponse();
        response.setNotifications(notifications);

        // Verify the list is correctly retrieved
        assertEquals("The number of notifications should match", 2, response.getNotifications().size());
        assertEquals("The first notification city should be Toronto", "Toronto", response.getNotifications().get(0).getCity());
        assertEquals("The second notification disaster type should be Earthquake", "Earthquake", response.getNotifications().get(1).getDisastertype());
    }

    /*** Sad Path Tests for NotificationHistoryResponse class ***/
    @Test
    public void testSetNullNotifications() {
        // Set notifications to null
        NotificationHistoryResponse response = new NotificationHistoryResponse();
        response.setNotifications(null);

        // Verify the list is null
        assertNull("The notifications list should be null", response.getNotifications());
    }

    @Test
    public void testSetEmptyNotifications() {
        // Set notifications to an empty list
        NotificationHistoryResponse response = new NotificationHistoryResponse();
        response.setNotifications(new ArrayList<>());

        // Verify the list is empty
        assertTrue("The notifications list should be empty", response.getNotifications().isEmpty());
    }

    /*** Happy Path Tests for NotificationResponse class ***/
    @Test
    public void testNotificationResponseConstructor() {
        // Test the constructor with all fields
        NotificationResponse response = new NotificationResponse(
                "System1", -79.3832, 43.6532, "Toronto", "Flood", 3, "2024-11-25",
                "Stay indoors", "Evacuate", "Seek shelter"
        );

        // Validate that all fields are correctly initialized
        assertEquals("System1", response.getNotiforigin());
        assertEquals(-79.3832, response.getLongitude(), 0.0001);
        assertEquals(43.6532, response.getLatitude(), 0.0001);
        assertEquals("Toronto", response.getCity());
        assertEquals("Flood", response.getDisastertype());
        assertEquals(3, response.getDisasterlevel());
        assertEquals("2024-11-25", response.getNotifdate());
        assertEquals("Stay indoors", response.getPreparationsteps());
        assertEquals("Evacuate", response.getActivesteps());
        assertEquals("Seek shelter", response.getRecoverysteps());
    }

    @Test
    public void testNotificationResponseRequiredFieldsConstructor() {
        // Test the constructor with only required fields
        NotificationResponse response = new NotificationResponse(
                "System2", -123.1216, 49.2827, "Vancouver", "Earthquake", 5, "2024-11-25"
        );

        // Validate that required fields are correctly initialized
        assertEquals("System2", response.getNotiforigin());
        assertEquals(-123.1216, response.getLongitude(), 0.0001);
        assertEquals(49.2827, response.getLatitude(), 0.0001);
        assertEquals("Vancouver", response.getCity());
        assertEquals("Earthquake", response.getDisastertype());
        assertEquals(5, response.getDisasterlevel());
        assertEquals("2024-11-25", response.getNotifdate());

        // Validate that optional fields are null by default
        assertNull(response.getPreparationsteps());
        assertNull(response.getActivesteps());
        assertNull(response.getRecoverysteps());
    }

    @Test
    public void testNotificationResponseSettersAndGetters() {
        // Test the setters and getters for all fields
        NotificationResponse response = new NotificationResponse(
                "System3", 0, 0, "PlaceholderCity", "PlaceholderDisaster", 0, "PlaceholderDate"
        );

        // Update fields using setters
        response.setNotiforigin("UpdatedSystem");
        response.setLongitude(-80.1234);
        response.setLatitude(44.5678);
        response.setCity("UpdatedCity");
        response.setDisastertype("Fire");
        response.setDisasterlevel(2);
        response.setNotifdate("2024-11-26");
        response.setPreparationsteps("Prepare kits");
        response.setActivesteps("Evacuate quickly");
        response.setRecoverysteps("Rebuild");

        // Validate that updated values are correctly set using getters
        assertEquals("UpdatedSystem", response.getNotiforigin());
        assertEquals(-80.1234, response.getLongitude(), 0.0001);
        assertEquals(44.5678, response.getLatitude(), 0.0001);
        assertEquals("UpdatedCity", response.getCity());
        assertEquals("Fire", response.getDisastertype());
        assertEquals(2, response.getDisasterlevel());
        assertEquals("2024-11-26", response.getNotifdate());
        assertEquals("Prepare kits", response.getPreparationsteps());
        assertEquals("Evacuate quickly", response.getActivesteps());
        assertEquals("Rebuild", response.getRecoverysteps());
    }

    /*** Sad Path Tests for NotificationResponse class ***/
    @Test
    public void testNotificationResponseNullOptionalFields() {
        // Create a response with valid required fields
        NotificationResponse response = new NotificationResponse(
                "System4", -79.3832, 43.6532, "Toronto", "Flood", 3, "2024-11-25"
        );

        // Set optional fields to null
        response.setPreparationsteps(null);
        response.setActivesteps(null);
        response.setRecoverysteps(null);

        // Validate that optional fields are null
        assertNull(response.getPreparationsteps());
        assertNull(response.getActivesteps());
        assertNull(response.getRecoverysteps());
    }

    @Test
    public void testNotificationResponseInvalidLongitudeAndLatitude() {
        // Create a response with default valid longitude and latitude
        NotificationResponse response = new NotificationResponse(
                "System5", 0, 0, "Toronto", "Flood", 3, "2024-11-25"
        );

        // Set invalid longitude and latitude values
        response.setLongitude(200.0); // Invalid longitude
        response.setLatitude(-100.0); // Invalid latitude

        // Validate that invalid values are set without exceptions
        assertEquals(200.0, response.getLongitude(), 0.0001);
        assertEquals(-100.0, response.getLatitude(), 0.0001);
    }

    @Test
    public void testNotificationResponseInvalidDisasterLevel() {
        // Create a response with an invalid disaster level
        NotificationResponse response = new NotificationResponse(
                "System6", -79.3832, 43.6532, "Toronto", "Flood", -1, "2024-11-25"
        );

        // Validate that the disaster level is set to the invalid value
        assertEquals(-1, response.getDisasterlevel());
    }
}
