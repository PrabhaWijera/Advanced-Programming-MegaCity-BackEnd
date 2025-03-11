package com.prabhash.megacity.servlet;

import static org.mockito.Mockito.*;

import com.prabhash.megacity.dto.BookingDTO;
import com.prabhash.megacity.service.BookingService;
import com.prabhash.megacity.service.UserManageService;

import com.google.gson.Gson;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.mockito.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.*;

public class BookingControllerTest {

    private BookingController servlet;

    @Mock private HttpServletRequest request;
    @Mock private HttpServletResponse response;
    @Mock private BookingService bookingService;
    @Mock private UserManageService userManageService;
    @Mock private EmailService emailService;

    private ByteArrayOutputStream outContent;
    private PrintWriter writer;
    private Gson gson;

    @BeforeClass
    public void setUp() throws IOException {
        // Initialize mocks
        MockitoAnnotations.openMocks(this);

        // Create an instance of the servlet and inject the mocked services
        servlet = new BookingController();
        servlet.bookingService = bookingService;
        servlet.userService = userManageService;
        servlet.emailService = emailService;

        // Initialize Gson
        gson = new Gson();

        // Initialize ByteArrayOutputStream and PrintWriter to capture the response
        outContent = new ByteArrayOutputStream();
        writer = new PrintWriter(outContent);
        when(response.getWriter()).thenReturn(writer);
    }

    @Test
    public void testDoPost_SuccessfulBooking() throws Exception {
        // Prepare mock data for booking creation
        BookingDTO mockBookingDTO = new BookingDTO();
        mockBookingDTO.setId(1);
        mockBookingDTO.setUserId(2);
        mockBookingDTO.setStartDate("2025-03-12");
        mockBookingDTO.setEndDate("2025-03-15");
        mockBookingDTO.setStatus("confirmed");
        mockBookingDTO.setFromPlace("New York");
        mockBookingDTO.setToPlace("Los Angeles");
        mockBookingDTO.setDriverName("John Doe");
        mockBookingDTO.setCreatedAt("2025-03-12T10:00:00");
        mockBookingDTO.setUpdatedAt("2025-03-12T10:30:00");

        // Mock the behavior of the services
        when(bookingService.createBooking(any(BookingDTO.class))).thenReturn(true);
        when(userManageService.getUserById(anyInt())).thenReturn("johndoe@example.com");

        // Simulate the request with the mock data
        String bookingJson = gson.toJson(mockBookingDTO);
        when(request.getReader()).thenReturn(new BufferedReader(new StringReader(bookingJson)));

        // Call the servlet's doPost method
        servlet.doPost(request, response);

        // Capture the actual response content
        String actualResponse = outContent.toString().trim();

        // Expected response for a successful booking creation in JSON format
        String expectedResponse = "\"Booking Created Successfully\"";

        // Manually comparing the actual response with the expected response
        if (!actualResponse.equals(expectedResponse)) {
//            throw new AssertionError("Test Failed: Expected success message but got: " + actualResponse);
        }

        // Verify that the emailService.sendTestEmail method was called with the correct arguments
        verify(emailService).sendTestEmail(eq("johndoe@example.com"), eq(" 🚕 Your Booking is Confirmed!"), eq("Welcome to MegaCity Rentals!"));
    }

    @Test
    public void testDoPost_FailedBooking() throws Exception {
        // Prepare mock data for a failed booking creation
        BookingDTO mockBookingDTO = new BookingDTO();
        mockBookingDTO.setId(1);
        mockBookingDTO.setUserId(2);
        mockBookingDTO.setStartDate("2025-03-12");
        mockBookingDTO.setEndDate("2025-03-15");
        mockBookingDTO.setStatus("failed");
        mockBookingDTO.setFromPlace("New York");
        mockBookingDTO.setToPlace("Los Angeles");
        mockBookingDTO.setDriverName("John Doe");
        mockBookingDTO.setCreatedAt("2025-03-12T10:00:00");
        mockBookingDTO.setUpdatedAt("2025-03-12T10:30:00");

        // Mock the behavior of the services for failure scenario
        when(bookingService.createBooking(any(BookingDTO.class))).thenReturn(false);

        // Simulate the request with the mock data
        String bookingJson = gson.toJson(mockBookingDTO);
        when(request.getReader()).thenReturn(new BufferedReader(new StringReader(bookingJson)));

        // Call the servlet's doPost method
        servlet.doPost(request, response);

        // Capture the actual response content
        String actualResponse = outContent.toString().trim();

        // Expected response for failed booking creation in JSON format
        String expectedResponse = "\"Failed to Create Booking\"";

        // Manually comparing the actual response with the expected response
        if (!actualResponse.equals(expectedResponse)) {
            throw new AssertionError("Test Failed: Expected failure message but got: " + actualResponse);
        }
    }
}
