package com.prabhash.megacity.servlet;

import static org.mockito.Mockito.*;

import com.prabhash.megacity.dto.ErrorResponse;
import com.prabhash.megacity.entity.User;
import com.prabhash.megacity.service.UserService;
import com.prabhash.megacity.util.filters.JwtUtil;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.mockito.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

public class LoginServletTest {

    private LoginServlet servlet;

    @Mock private HttpServletRequest request;
    @Mock private HttpServletResponse response;
    @Mock private UserService userService;

    private ByteArrayOutputStream outContent;
    private PrintWriter writer;

    @BeforeClass
    public void setUp() throws IOException {
        // Initialize mocks using Mockito
        MockitoAnnotations.openMocks(this);

        // Initialize servlet and inject the mocked userService
        servlet = new LoginServlet();
        servlet.userService = userService;

        // Initialize ByteArrayOutputStream and PrintWriter for capturing the response
        outContent = new ByteArrayOutputStream();
        writer = new PrintWriter(outContent);
        when(response.getWriter()).thenReturn(writer);  // Capture response output
    }

    @Test
    public void testDoPost_SuccessfulLogin() throws Exception {
        try {
            // Mocking input parameters (email and password)
            when(request.getParameter("email")).thenReturn("johndoe@example.com");
            when(request.getParameter("password")).thenReturn("Password123!");

            // Mocking UserService behavior for successful authentication
            User mockUser = new User(1, "johnDoe", "Password123!", "johndoe@example.com", "0760368023", "customer");
            when(userService.authenticateUser(anyString(), anyString())).thenReturn(mockUser);

            // Mocking JWT generation
            String mockToken = "mock_token";  // This is the mock token
            when(JwtUtil.generateToken(anyString(), anyString())).thenReturn(mockToken);

            // Call the servlet's doPost method
            servlet.doPost(request, response);

            // Capture the actual response content
            String actualResponse = outContent.toString().trim();

            // Expected response for successful login (including mock token)
            String expectedResponse = "{\"token\":\"mock_token\"}";

            // Assert that the actual response matches the expected response
            assert actualResponse.equals(expectedResponse) : "Test Failed: Expected token response but got: " + actualResponse;

            // Verifying that the response status is 200 OK
            verify(response).setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Test failed due to unexpected exception: " + e.getMessage());
        }
    }


    @Test
    public void testDoPost_InvalidLogin() throws Exception {
        try {
            // Mocking invalid input parameters
            when(request.getParameter("email")).thenReturn("john.doe@example.com");
            when(request.getParameter("password")).thenReturn("WrongPassword");

            // Mocking UserService behavior for failed authentication (user is null)
            when(userService.authenticateUser(anyString(), anyString())).thenReturn(null);

            // Call the servlet's doPost method
            servlet.doPost(request, response);

            // Capture the actual response content
            String actualResponse = outContent.toString().trim();

            // Expected response for invalid login
            String expectedResponse = "{\"error\":\"Invalid login credentials\"}";

            // Check if the actual response matches the expected response
            assert actualResponse.equals(expectedResponse) : "Test Failed: Expected error message but got: " + actualResponse;

            // Verifying that the response status is 401 Unauthorized
            verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Test failed due to unexpected exception: " + e.getMessage());
        }
    }


}
