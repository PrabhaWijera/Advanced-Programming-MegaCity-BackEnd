package com.prabhash.megacity.servlet;

import com.prabhash.megacity.dto.CarDTO;
import com.prabhash.megacity.service.CarService;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.*;

import static org.testng.Assert.assertEquals;

public class CarServletTest {

    private HttpServletRequest request;
    private HttpServletResponse response;
    private PrintWriter printWriter;
    private CarService carService;
    private CarImageServlet carServlet; // The servlet we are testing

    @BeforeMethod
    public void setup() throws IOException {
        // Initialize the PrintWriter mock
        printWriter = new PrintWriter(System.out);

        // Create the HttpServletRequest and HttpServletResponse directly or use a simple mock
        request = new FakeHttpServletRequest();
        response = new FakeHttpServletResponse(printWriter);

        // Initialize your CarService directly or mock it
        carService = new InMemoryCarService(); // Using in-memory service instead of mock
        carServlet = new CarImageServlet(); // Assuming the servlet has a constructor

        // Set the CarService in the servlet (if required)
     //   carServlet.setCarService(carService); // Assuming you have a setter method in CarImageServlet for CarService
    }

    // Test for adding a car with valid data
    @Test
    public void testAddCarWithValidData() throws Exception {
        // Simulating valid input parameters
        ((FakeHttpServletRequest) request).setParameter("name", "Toyota");
        ((FakeHttpServletRequest) request).setParameter("model", "ToyotaCorolla");
        ((FakeHttpServletRequest) request).setParameter("plate_number", "ABC-123");
        ((FakeHttpServletRequest) request).setParameter("year", "2020");
        ((FakeHttpServletRequest) request).setParameter("status", "Available");

        // Execute the method
        carServlet.addCarWithImage(request, response);

        // Validate response status and output
        assertEquals(response.getStatus(), HttpServletResponse.SC_OK);
        assertEquals(printWriter.toString().trim(), "{\"message\": \"Car added successfully.\"}");
    }

    // Test for adding a car with an invalid car name
    @Test
    public void testAddCarWithInvalidCarName() throws Exception {
        // Simulating invalid input parameters
        ((FakeHttpServletRequest) request).setParameter("name", "A"); // Invalid name (too short)
        ((FakeHttpServletRequest) request).setParameter("model", "ToyotaCorolla");
        ((FakeHttpServletRequest) request).setParameter("plate_number", "ABC-123");
        ((FakeHttpServletRequest) request).setParameter("year", "2020");
        ((FakeHttpServletRequest) request).setParameter("status", "Available");

        // Execute the method
        carServlet.addCarWithImage(request, response);

        // Validate error response
        assertEquals(response.getStatus(), HttpServletResponse.SC_BAD_REQUEST);
        assertEquals(printWriter.toString().trim(), "{\"error\": \"Car name must be at least 2 characters long!\"}");
    }

    // Test for adding a car with an invalid car model
    @Test
    public void testAddCarWithInvalidCarModel() throws Exception {
        // Simulating invalid input parameters
        ((FakeHttpServletRequest) request).setParameter("name", "Toyota");
        ((FakeHttpServletRequest) request).setParameter("model", "C"); // Invalid model (too short)
        ((FakeHttpServletRequest) request).setParameter("plate_number", "ABC-123");
        ((FakeHttpServletRequest) request).setParameter("year", "2020");
        ((FakeHttpServletRequest) request).setParameter("status", "Available");

        // Execute the method
        carServlet.addCarWithImage(request, response);

        // Validate error response
        assertEquals(response.getStatus(), HttpServletResponse.SC_BAD_REQUEST);
        assertEquals(printWriter.toString().trim(), "{\"error\": \"Car model must be at least 2 characters long!\"}");
    }

    // Test for adding a car with an invalid year
    @Test
    public void testAddCarWithInvalidYear() throws Exception {
        // Simulating invalid input parameters
        ((FakeHttpServletRequest) request).setParameter("name", "Toyota");
        ((FakeHttpServletRequest) request).setParameter("model", "Corolla");
        ((FakeHttpServletRequest) request).setParameter("plate_number", "ABC-123");
        ((FakeHttpServletRequest) request).setParameter("year", "1800"); // Invalid year
        ((FakeHttpServletRequest) request).setParameter("status", "Available");

        // Execute the method
        carServlet.addCarWithImage(request, response);

        // Validate error response
        assertEquals(response.getStatus(), HttpServletResponse.SC_BAD_REQUEST);
        assertEquals(printWriter.toString().trim(), "{\"error\": \"Year must be between 1900 and the current year!\"}");
    }

    // Test for adding a car with missing status
    @Test
    public void testAddCarWithMissingStatus() throws Exception {
        // Simulating missing status
        ((FakeHttpServletRequest) request).setParameter("name", "Toyota");
        ((FakeHttpServletRequest) request).setParameter("model", "Corolla");
        ((FakeHttpServletRequest) request).setParameter("plate_number", "ABC-123");
        ((FakeHttpServletRequest) request).setParameter("year", "2020");
        ((FakeHttpServletRequest) request).setParameter("status", ""); // Missing status

        // Execute the method
        carServlet.addCarWithImage(request, response);

        // Validate error response
        assertEquals(response.getStatus(), HttpServletResponse.SC_BAD_REQUEST);
        assertEquals(printWriter.toString().trim(), "{\"error\": \"Car status is required!\"}");
    }

    // Simple in-memory implementation of CarService
    private static class InMemoryCarService implements CarService {
        @Override
        public CarDTO addCar(CarDTO carDTO) {
            // Simulate adding a car (you can add more logic as needed)
            return new CarDTO(1, carDTO.getName(), carDTO.getModel(), carDTO.getPlate_number(), carDTO.getYear(), carDTO.getStatus());
        }

        @Override
        public CarDTO getCarById(int id) {
            return null; // Not used in the test
        }

        @Override
        public List<CarDTO> getAllCars() {
            return null; // Not used in the test
        }

        @Override
        public void updateCar(CarDTO carDTO) {
            // Not used in the test
        }

        @Override
        public void deleteCar(int id) {
            // Not used in the test
        }
    }

    // Simple mock implementation of HttpServletRequest
    private static class FakeHttpServletRequest implements HttpServletRequest {
        private final java.util.Map<String, String> parameters = new java.util.HashMap<>();

        public void setParameter(String name, String value) {
            parameters.put(name, value);
        }

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

        @Override
        public String getProtocol() {
            return "";
        }

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

        @Override
        public String getRemoteAddr() {
            return "";
        }

        @Override
        public String getRemoteHost() {
            return "";
        }

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

        @Override
        public String getAuthType() {
            return "";
        }

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

        @Override
        public String getMethod() {
            return "";
        }

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

        @Override
        public String getRequestURI() {
            return "";
        }

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

        // Implement other required methods from HttpServletRequest (left out for brevity)
    }

    // Simple mock implementation of HttpServletResponse
    private static class FakeHttpServletResponse implements HttpServletResponse {
        private final PrintWriter writer;
        private int status;

        public FakeHttpServletResponse(PrintWriter writer) {
            this.writer = writer;
        }

        @Override
        public void addCookie(Cookie cookie) {

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

        @Override
        public void setStatus(int sc) {
            this.status = sc;
        }

        @Override
        public int getStatus() {
            return this.status;
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
            return writer;
        }

        @Override
        public void setCharacterEncoding(String encoding) {

        }

        @Override
        public void setContentLength(int len) {

        }

        @Override
        public void setContentLengthLong(long len) {

        }

        @Override
        public void setContentType(String type) {

        }

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

        // Implement other required methods from HttpServletResponse (left out for brevity)
    }
}
