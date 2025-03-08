package com.prabhash.megacity.servlet;

import com.prabhash.megacity.entity.User;
import com.prabhash.megacity.service.UserService;
import com.prabhash.megacity.service.WhatsAppMessageService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.PrintWriter;

import static org.mockito.Mockito.*;

public class RegisterServletTest {

    private RegisterServlet registerServlet;
    private UserService mockUserService;
    private WhatsAppMessageService mockMessageService;
    private HttpServletRequest mockRequest;
    private HttpServletResponse mockResponse;
    private PrintWriter mockWriter;

    @BeforeMethod
    public void setUp() throws Exception {
        // Mock dependencies
        mockUserService = mock(UserService.class);
        mockMessageService = mock(WhatsAppMessageService.class);
        mockRequest = mock(HttpServletRequest.class);
        mockResponse = mock(HttpServletResponse.class);
        mockWriter = mock(PrintWriter.class);

        // Instantiate the servlet with the mock dependencies
        registerServlet = new RegisterServlet();
        registerServlet.userService = mockUserService;
        registerServlet.messageService = mockMessageService;

        // Mock response writer
        when(mockResponse.getWriter()).thenReturn(mockWriter);
    }

    @Test(priority = 1)
    public void testRegisterUser_Success() throws Exception {
        // Arrange: mock request parameters
        when(mockRequest.getParameter("username")).thenReturn("john_doe");
        when(mockRequest.getParameter("password")).thenReturn("password123");
        when(mockRequest.getParameter("email")).thenReturn("john.doe@example.com");
        when(mockRequest.getParameter("phone")).thenReturn("1234567890");
        when(mockRequest.getParameter("role")).thenReturn("customer");

        User mockUser = new User(0, "john_doe", "password123", "john.doe@example.com", "1234567890", "user");

        // Mock the registerUser method to return true (indicating success)
        when(mockUserService.registerUser(any(User.class))).thenReturn(true);

        // Act: call the doPost method (registering user)
        registerServlet.doPost(mockRequest, mockResponse);

        // Assert: Verify that correct methods were called
        verify(mockUserService).registerUser(any(User.class)); // Check that user registration is called
        verify(mockMessageService).sendRegistrationWhatsAppMessage("1234567890", "john_doe"); // Verify WhatsApp message sent
        verify(mockResponse).setStatus(HttpServletResponse.SC_OK); // Ensure response status is 200 OK
        verify(mockWriter).print("{\"message\":\"Registration successful!\"}");
    }

    @Test(priority = 2)
    public void testRegisterUser_Failure() throws Exception {
        // Arrange: mock request parameters
        when(mockRequest.getParameter("username")).thenReturn("john_doe");
        when(mockRequest.getParameter("password")).thenReturn("password123");
        when(mockRequest.getParameter("email")).thenReturn("john.doe@example.com");
        when(mockRequest.getParameter("phone")).thenReturn("1234567890");
        when(mockRequest.getParameter("role")).thenReturn("customer");

        User mockUser = new User(0, "john_doe", "password123", "john.doe@example.com", "1234567890", "user");

        // Mock the registerUser method to return false (indicating failure)
        when(mockUserService.registerUser(any(User.class))).thenReturn(false);

        // Act: call the doPost method (attempt to register user)
        registerServlet.doPost(mockRequest, mockResponse);

        // Assert: Verify that correct methods were called
        verify(mockUserService).registerUser(any(User.class)); // Check that user registration is called
        verify(mockResponse).setStatus(HttpServletResponse.SC_BAD_REQUEST); // Ensure response status is 400 BAD REQUEST
     //   verify(mockWriter).print("{ \"error\": \"Failed to register.\" }"); // Ensure failure message is printed
        verify(mockWriter).print("{\"message\":\"Failed to register.\"}");
    }
}
