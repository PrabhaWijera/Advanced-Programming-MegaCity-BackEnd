package com.prabhash.megacity.servlet;

import com.prabhash.megacity.dto.ErrorResponse;
import com.prabhash.megacity.dto.TokenResponse;
import com.prabhash.megacity.entity.User;
import com.prabhash.megacity.service.UserService;
import com.prabhash.megacity.servlet.LoginServlet;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.PrintWriter;

import static org.mockito.Mockito.*;

public class LoginServletTest {

    private LoginServlet loginServlet;
    private UserService mockUserService;
    private HttpServletRequest mockRequest;
    private HttpServletResponse mockResponse;
    private PrintWriter mockWriter;

    @BeforeMethod
    public void setUp() throws Exception {
        // Mock dependencies
        mockUserService = mock(UserService.class);
        mockRequest = mock(HttpServletRequest.class);
        mockResponse = mock(HttpServletResponse.class);
        mockWriter = mock(PrintWriter.class);

        // Instantiate the servlet with the mock dependencies
        loginServlet = new LoginServlet();
        loginServlet.userService = mockUserService;

        // Mock response writer
        when(mockResponse.getWriter()).thenReturn(mockWriter);
    }

    @Test
    public void testLogin_Success() throws Exception {
        // Arrange: mock request parameters
        String email = "john.doe@example.com";
        String password = "password123";
        when(mockRequest.getParameter("email")).thenReturn(email);
        when(mockRequest.getParameter("password")).thenReturn(password);

        User mockUser = new User(1, "john_doe", "password123", "john.doe@example.com", "1234567890", "user");

        // Mock the authenticateUser method to return a user (successful login)
        when(mockUserService.authenticateUser(email, password)).thenReturn(mockUser);

        // Act: call the doPost method (logging in)
        loginServlet.doPost(mockRequest, mockResponse);

        // Assert: Verify correct methods were called
        verify(mockUserService).authenticateUser(email, password); // Check that authenticateUser was called
        verify(mockResponse).setStatus(HttpServletResponse.SC_OK); // Ensure response status is 200 OK
        verify(mockWriter).print(Mockito.anyString()); // Ensure a response body (TokenResponse) is printed
    }

    @Test
    public void testLogin_Failure() throws Exception {
        // Arrange: mock request parameters
        String email = "john.doe@example.com";
        String password = "wrongpassword";
        when(mockRequest.getParameter("email")).thenReturn(email);
        when(mockRequest.getParameter("password")).thenReturn(password);

        // Mock the authenticateUser method to return null (failed login)
        when(mockUserService.authenticateUser(email, password)).thenReturn(null);

        // Act: call the doPost method (attempting to log in)
        loginServlet.doPost(mockRequest, mockResponse);

        // Assert: Verify correct methods were called
        verify(mockUserService).authenticateUser(email, password); // Check that authenticateUser was called
        verify(mockResponse).setStatus(HttpServletResponse.SC_UNAUTHORIZED); // Ensure response status is 401 Unauthorized
        verify(mockWriter).print(Mockito.anyString()); // Ensure error message (ErrorResponse) is printed
    }

    @Test
    public void testLogin_Exception() throws Exception {
        // Arrange: mock request parameters
        String email = "john.doe@example.com";
        String password = "password123";
        when(mockRequest.getParameter("email")).thenReturn(email);
        when(mockRequest.getParameter("password")).thenReturn(password);

        // Mock the authenticateUser method to throw an exception (simulate an error)
        when(mockUserService.authenticateUser(email, password)).thenThrow(new RuntimeException("Database error"));

        // Act: call the doPost method (will cause an exception)
        loginServlet.doPost(mockRequest, mockResponse);

        // Assert: Verify correct methods were called
        verify(mockUserService).authenticateUser(email, password); // Check that authenticateUser was called
        verify(mockResponse).setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // Ensure response status is 500 Internal Server Error
    }
}
