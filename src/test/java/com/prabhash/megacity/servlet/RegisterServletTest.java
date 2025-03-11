package com.prabhash.megacity.servlet;

import static org.mockito.Mockito.*;

import com.prabhash.megacity.service.UserService;
import com.prabhash.megacity.service.WhatsAppMessageService;
import com.prabhash.megacity.service.impl.SmsServiceImpl;
import com.prabhash.megacity.service.impl.UserServiceImpl;
import com.prabhash.megacity.service.impl.WhatsAppMessageServiceImpl;
import com.prabhash.megacity.entity.User;
import com.prabhash.megacity.config.ResponseMessage;
import com.google.gson.Gson;
import com.prabhash.megacity.servlet.RegisterServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.mockito.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class RegisterServletTest {

    private RegisterServlet servlet;
    @Mock private HttpServletRequest request;
    @Mock private HttpServletResponse response;
    @Mock private UserService userService;
    @Mock private WhatsAppMessageService messageService;
    @Mock private SmsServiceImpl smsService;
    private ByteArrayOutputStream outContent; // For capturing the response
    private PrintWriter writer;

    @BeforeClass
    public void setUp() throws IOException {
        // Initialize mocks using Mockito
        MockitoAnnotations.openMocks(this); // This works for TestNG as well
        servlet = new RegisterServlet();
        servlet.userService = userService;
        servlet.messageService = messageService;
        servlet.smsService = smsService;

        // Initialize ByteArrayOutputStream and PrintWriter
        outContent = new ByteArrayOutputStream();
        writer = new PrintWriter(outContent);
        when(response.getWriter()).thenReturn(writer);  // Capture response output
    }

    @Test
    public void testDoPost_SuccessfulRegistration() throws Exception {
        // Mocking input parameters
        when(request.getParameter("username")).thenReturn("johnDoe");
        when(request.getParameter("password")).thenReturn("Password123!");
        when(request.getParameter("email")).thenReturn("johndoe@example.com");
        when(request.getParameter("phone")).thenReturn("0760368023");
        when(request.getParameter("role")).thenReturn("customer");

        // Mocking UserService behavior
        when(userService.registerUser(any(User.class))).thenReturn(true);

        // Call the servlet's doPost method
        servlet.doPost(request, response);

        // Capture the actual response content
        String actualResponse = outContent.toString().trim(); // Capture response as a string

        // Expected response for successful registration
        String expectedResponse = "{\"message\":\"Registration successful!\"}";

        // Compare the actual and expected responses
        if (actualResponse.equals(expectedResponse)) {
            System.out.println("Test Passed: Successful registration response found.");
        } else {
            System.out.println("Test Failed: Expected success message but got: " + actualResponse);
        }

        // Verifying that the response status is 200 OK
        verify(response).setStatus(HttpServletResponse.SC_OK);
    }

    @Test
    public void testDoPost_ValidationFailed() throws Exception {
        // Mocking invalid input parameters
        when(request.getParameter("username")).thenReturn("johnoe");
        when(request.getParameter("password")).thenReturn("Password123!");
        when(request.getParameter("email")).thenReturn("johndoxample.com");
        when(request.getParameter("phone")).thenReturn("076036802");


        // Call the servlet's doPost method
        servlet.doPost(request, response);

        // Capture the actual response content
        String actualResponse = outContent.toString().trim();

        // Expected response for validation failure
        String expectedResponse = "{\"message\":\"Validation failed\",\"errors\":[\"Invalid username. It should be 3-20 characters long and contain only letters and numbers.\",\"Invalid password. It should be at least 8 characters long and include at least one letter, one number, and one special character.\",\"Invalid email format.\",\"Invalid phone number. It should contain only digits and be between 10-15 characters long.\"]}";

        // Compare the actual and expected responses
        if (actualResponse.equals(expectedResponse)) {
            System.out.println("Test Passed: Validation failed response found.");
        } else {
            System.out.println("Test Failed: Expected validation error message but got: " + actualResponse);
        }

        // Verifying that the response status is 400 Bad Request
        verify(response).setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Test
    public void testDoPost_FailedRegistration() throws Exception {
        // Mocking valid input parameters
        when(request.getParameter("username")).thenReturn("johnDoe");
        when(request.getParameter("password")).thenReturn("Password123!");
        when(request.getParameter("email")).thenReturn("johndoe@example.com");
        when(request.getParameter("phone")).thenReturn("0760368023");
        when(request.getParameter("role")).thenReturn("customer");
        // Mocking UserService behavior for failed registration
        when(userService.registerUser(any(User.class))).thenReturn(false);

        // Call the servlet's doPost method
        servlet.doPost(request, response);

        // Capture the actual response content
        String actualResponse = outContent.toString().trim();

        // Expected response for failed registration
        String expectedResponse = "{\"message\":\"Failed to register.\"}";

        // Compare the actual and expected responses
        if (actualResponse.equals(expectedResponse)) {
            System.out.println("Test Passed: Failed registration response found.");
        } else {
            System.out.println("Test Failed: Expected failure message but got: " + actualResponse);
        }

        // Verifying that the response status is 400 Bad Request
        verify(response).setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }
}
