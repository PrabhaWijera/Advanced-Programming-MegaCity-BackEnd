package com.prabhash.megacity.servlet;

import com.google.gson.Gson;
import com.prabhash.megacity.entity.User;
import com.prabhash.megacity.service.UserService;
import com.prabhash.megacity.service.impl.UserServiceImpl;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.*;
import java.security.Principal;
import java.util.*;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class LoginServletTest {

    private LoginServlet loginServlet;  // The servlet under test
    private UserService userService;    // The UserService instance
    private HttpServletRequest request;  // The HttpServletRequest instance
    private HttpServletResponse response;  // The HttpServletResponse instance
    private ByteArrayOutputStream responseOutputStream;  // For capturing the response output

    @BeforeMethod
    public void setUp() {
        // Instantiate the UserService directly (no mocking)
        userService = new UserServiceImpl();

        // Create the servlet instance
        loginServlet = new LoginServlet();

        // Create a custom HttpServletRequest and HttpServletResponse (we will simulate them)
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();

        // Prepare an output stream to capture the response
        responseOutputStream = new ByteArrayOutputStream();
        ((MockHttpServletResponse) response).setOutputStream(responseOutputStream);
    }

    @Test
    public void testSuccessfulLogin() throws Exception {
        // Arrange: Simulate the request with email and password
        ((MockHttpServletRequest) request).setParameter("email", "prabhashwijerathna2001@gmail.com");
        ((MockHttpServletRequest) request).setParameter("password", "prabhash2001");

        // Act: Call the doPost method to simulate the login process
        loginServlet.doPost(request, response);

        // Assert: Verify the response status is 200 (OK)
        assertEquals(response.getStatus(), HttpServletResponse.SC_OK);

        // Assert: Verify the response contains a token
        String responseBody = responseOutputStream.toString();
        assertTrue(responseBody.contains("token"), "Response body should contain token");

        // Optionally: Parse the response and verify the structure (e.g., JSON token)
        Gson gson = new Gson();
        TokenResponse tokenResponse = gson.fromJson(responseBody, TokenResponse.class);
        assertTrue(tokenResponse.getToken().length() > 0, "Token should not be empty");
    }

    @Test
    public void testFailedLogin() throws Exception {
        // Arrange: Simulate the request with invalid credentials
        ((MockHttpServletRequest) request).setParameter("email", "prabhashwijerathna2001@gmail.com");
        ((MockHttpServletRequest) request).setParameter("password", "prabhash2001");

        // Act: Call the doPost method to simulate the login process
        loginServlet.doPost(request, response);

        // Assert: Verify the response status is 401 (Unauthorized)
        assertEquals(response.getStatus(), HttpServletResponse.SC_UNAUTHORIZED);

        // Assert: Verify the response contains the error message
        String responseBody = responseOutputStream.toString();
        assertTrue(responseBody.contains("Invalid login credentials"), "Response body should contain error message");
    }

    // Mock HttpServletRequest class to simulate request parameters
    private static class MockHttpServletRequest implements HttpServletRequest {
        private final java.util.Map<String, String> parameters = new java.util.HashMap<>();

        @Override
        public Object getAttribute(String name) {
            return null;
        }

        @Override
        public Enumeration<String> getAttributeNames() {
            return null;
        }

        @Override
        public String getCharacterEncoding() {
            return "";
        }

        @Override
        public void setCharacterEncoding(String encoding) throws UnsupportedEncodingException {

        }

        @Override
        public int getContentLength() {
            return 0;
        }

        @Override
        public long getContentLengthLong() {
            return 0;
        }

        @Override
        public String getContentType() {
            return "";
        }

        @Override
        public ServletInputStream getInputStream() throws IOException {
            return null;
        }

        @Override
        public String getParameter(String name) {
            return parameters.get(name);
        }

        @Override
        public Enumeration<String> getParameterNames() {
            return null;
        }

        @Override
        public String[] getParameterValues(String name) {
            return new String[0];
        }

        @Override
        public Map<String, String[]> getParameterMap() {
            return Map.of();
        }

        public void setParameter(String name, String value) {
            parameters.put(name, value);
        }

        // Other methods can be left unimplemented or can return defaults as needed
        @Override public String getMethod() { return "POST"; }

        @Override
        public String getPathInfo() {
            return "";
        }

        @Override
        public String getPathTranslated() {
            return "";
        }

        @Override
        public String getContextPath() {
            return "";
        }

        @Override
        public String getQueryString() {
            return "";
        }

        @Override
        public String getRemoteUser() {
            return "";
        }

        @Override
        public boolean isUserInRole(String role) {
            return false;
        }

        @Override
        public Principal getUserPrincipal() {
            return null;
        }

        @Override
        public String getRequestedSessionId() {
            return "";
        }

        @Override public String getRequestURI() { return "/login"; }

        @Override
        public StringBuffer getRequestURL() {
            return null;
        }

        @Override
        public String getServletPath() {
            return "";
        }

        @Override
        public HttpSession getSession(boolean create) {
            return null;
        }

        @Override
        public HttpSession getSession() {
            return null;
        }

        @Override
        public String changeSessionId() {
            return "";
        }

        @Override
        public boolean isRequestedSessionIdValid() {
            return false;
        }

        @Override
        public boolean isRequestedSessionIdFromCookie() {
            return false;
        }

        @Override
        public boolean isRequestedSessionIdFromURL() {
            return false;
        }

        @Override
        public boolean authenticate(HttpServletResponse response) throws IOException, ServletException {
            return false;
        }

        @Override
        public void login(String username, String password) throws ServletException {

        }

        @Override
        public void logout() throws ServletException {

        }

        @Override
        public Collection<Part> getParts() throws IOException, ServletException {
            return List.of();
        }

        @Override
        public Part getPart(String name) throws IOException, ServletException {
            return null;
        }

        @Override
        public <T extends HttpUpgradeHandler> T upgrade(Class<T> handlerClass) throws IOException, ServletException {
            return null;
        }

        @Override public String getProtocol() { return "HTTP/1.1"; }

        @Override
        public String getScheme() {
            return "";
        }

        @Override
        public String getServerName() {
            return "";
        }

        @Override
        public int getServerPort() {
            return 0;
        }

        @Override
        public BufferedReader getReader() throws IOException {
            return null;
        }

        @Override public String getRemoteAddr() { return "127.0.0.1"; }
        @Override public String getRemoteHost() { return "localhost"; }

        @Override
        public void setAttribute(String name, Object o) {

        }

        @Override
        public void removeAttribute(String name) {

        }

        @Override
        public Locale getLocale() {
            return null;
        }

        @Override
        public Enumeration<Locale> getLocales() {
            return null;
        }

        @Override
        public boolean isSecure() {
            return false;
        }

        @Override
        public RequestDispatcher getRequestDispatcher(String path) {
            return null;
        }

        @Override
        public int getRemotePort() {
            return 0;
        }

        @Override
        public String getLocalName() {
            return "";
        }

        @Override
        public String getLocalAddr() {
            return "";
        }

        @Override
        public int getLocalPort() {
            return 0;
        }

        @Override
        public ServletContext getServletContext() {
            return null;
        }

        @Override
        public AsyncContext startAsync() throws IllegalStateException {
            return null;
        }

        @Override
        public AsyncContext startAsync(ServletRequest servletRequest, ServletResponse servletResponse) throws IllegalStateException {
            return null;
        }

        @Override
        public boolean isAsyncStarted() {
            return false;
        }

        @Override
        public boolean isAsyncSupported() {
            return false;
        }

        @Override
        public AsyncContext getAsyncContext() {
            return null;
        }

        @Override
        public DispatcherType getDispatcherType() {
            return null;
        }

        @Override
        public String getRequestId() {
            return "";
        }

        @Override
        public String getProtocolRequestId() {
            return "";
        }

        @Override
        public ServletConnection getServletConnection() {
            return null;
        }

        @Override public String getAuthType() { return null; }  // Return default value

        @Override
        public Cookie[] getCookies() {
            return new Cookie[0];
        }

        @Override
        public long getDateHeader(String name) {
            return 0;
        }

        @Override
        public String getHeader(String name) {
            return "";
        }

        @Override
        public Enumeration<String> getHeaders(String name) {
            return null;
        }

        @Override
        public Enumeration<String> getHeaderNames() {
            return null;
        }

        @Override
        public int getIntHeader(String name) {
            return 0;
        }
        // Add other necessary methods from HttpServletRequest
    }

    // Mock HttpServletResponse class to capture output
    private static class MockHttpServletResponse implements HttpServletResponse {
        private int status;
        private PrintWriter writer;
        private ByteArrayOutputStream outputStream;

        @Override
        public void setStatus(int sc) {
            this.status = sc;
        }

        @Override
        public int getStatus() {
            return status;
        }

        @Override
        public String getHeader(String name) {
            return "";
        }

        @Override
        public Collection<String> getHeaders(String name) {
            return List.of();
        }

        @Override
        public Collection<String> getHeaderNames() {
            return List.of();
        }

        @Override
        public String getCharacterEncoding() {
            return "";
        }

        @Override
        public String getContentType() {
            return "";
        }

        @Override
        public ServletOutputStream getOutputStream() throws IOException {
            return null;
        }

        @Override
        public PrintWriter getWriter() {
            if (writer == null) {
                writer = new PrintWriter(outputStream);
            }
            return writer;
        }

        @Override
        public void addCookie(Cookie cookie) {
            // Do nothing or can log the cookie if needed
        }

        @Override
        public boolean containsHeader(String name) {
            return false;
        }

        @Override
        public String encodeURL(String url) {
            return "";
        }

        @Override
        public String encodeRedirectURL(String url) {
            return "";
        }

        @Override
        public void sendError(int sc, String msg) throws IOException {

        }

        @Override
        public void sendError(int sc) throws IOException {

        }

        @Override
        public void sendRedirect(String location, int sc, boolean clearBuffer) throws IOException {

        }

        @Override
        public void setDateHeader(String name, long date) {

        }

        @Override
        public void addDateHeader(String name, long date) {

        }

        @Override
        public void setHeader(String name, String value) {

        }

        @Override
        public void addHeader(String name, String value) {

        }

        @Override
        public void setIntHeader(String name, int value) {

        }

        @Override
        public void addIntHeader(String name, int value) {

        }

        // Implement other necessary methods from HttpServletResponse
        @Override public void setContentType(String type) {}

        @Override
        public void setBufferSize(int size) {

        }

        @Override
        public int getBufferSize() {
            return 0;
        }

        @Override
        public void flushBuffer() throws IOException {

        }

        @Override
        public void resetBuffer() {

        }

        @Override
        public boolean isCommitted() {
            return false;
        }

        @Override
        public void reset() {

        }

        @Override
        public void setLocale(Locale loc) {

        }

        @Override
        public Locale getLocale() {
            return null;
        }

        @Override public void setCharacterEncoding(String charset) {}

        @Override
        public void setContentLength(int len) {

        }

        @Override
        public void setContentLengthLong(long len) {

        }

        public void setOutputStream(ByteArrayOutputStream os) {
            this.outputStream = os;
        }
    }

    // A simple class to represent the TokenResponse (you might already have this)
    public static class TokenResponse {
        private String token;

        public TokenResponse(String token) {
            this.token = token;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
