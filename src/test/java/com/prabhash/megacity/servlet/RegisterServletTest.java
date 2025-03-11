package com.prabhash.megacity.servlet;

import static org.mockito.Mockito.*;
import static org.testng.Assert.*;

import com.google.gson.Gson;
import com.prabhash.megacity.entity.User;
import com.prabhash.megacity.service.UserService;
import com.prabhash.megacity.service.impl.SmsServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collections;

public class RegisterServletTest {
    @InjectMocks
    private RegisterServlet registerServlet;

    @Mock
    private UserService userService;

    @Mock
    private EmailService emailService;

    @Mock
    private SmsServiceImpl smsService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    private StringWriter responseWriter;

    @BeforeMethod
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        registerServlet.userService = userService;
        registerServlet.emailService = emailService;
        registerServlet.smsService = smsService;

        responseWriter = new StringWriter();
        when(response.getWriter()).thenReturn(new PrintWriter(responseWriter));
    }

    @Test
    public void testSuccessfulRegistration() throws ServletException, IOException {
        when(request.getParameter("username")).thenReturn("test1001");
        when(request.getParameter("password")).thenReturn("test1012001");
        when(request.getParameter("email")).thenReturn("test1011@gmail.com");
        when(request.getParameter("phone")).thenReturn("0762235588");
        when(request.getParameter("role")).thenReturn("customer");
        when(userService.registerUser(any(User.class))).thenReturn(true);

        registerServlet.doPost(request, response);

        String jsonResponse = responseWriter.toString();
        assertTrue(jsonResponse.contains("Registration successful"));
        verify(emailService, times(1)).sendTestEmail(eq("test1011@gmail.com"), anyString(), anyString());
    }

    @Test
    public void testValidationFailure() throws ServletException, IOException {
        when(request.getParameter("username")).thenReturn("t");  // Invalid username
        when(request.getParameter("password")).thenReturn("short");  // Invalid password
        when(request.getParameter("email")).thenReturn("invalid_email");
        when(request.getParameter("phone")).thenReturn("123");
        when(request.getParameter("role")).thenReturn("customer");

        registerServlet.doPost(request, response);

        String jsonResponse = responseWriter.toString();
        assertTrue(jsonResponse.contains("Validation failed"));
    }

    @Test
    public void testRegistrationFailure() throws ServletException, IOException {
        when(request.getParameter("username")).thenReturn("test1001");
        when(request.getParameter("password")).thenReturn("test1012001");
        when(request.getParameter("email")).thenReturn("test1011@gmail.com");
        when(request.getParameter("phone")).thenReturn("0762235588");
        when(request.getParameter("role")).thenReturn("customer");
        when(userService.registerUser(any(User.class))).thenReturn(false);

        registerServlet.doPost(request, response);

        String jsonResponse = responseWriter.toString();
        assertTrue(jsonResponse.contains("Failed to register"));
    }
}
