package com.prabhash.megacity.servlet;

import com.prabhash.megacity.dto.ErrorResponse;
import com.prabhash.megacity.dto.TokenResponse;
import com.prabhash.megacity.entity.User;
import com.prabhash.megacity.service.UserService;
import com.prabhash.megacity.service.impl.UserServiceImpl;
import com.prabhash.megacity.util.filters.JwtUtil;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.PrintWriter;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    public UserService userService = new UserServiceImpl();

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, java.io.IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            // Authenticate the user
            User user = userService.authenticateUser(email, password);
            System.out.println("authenticateUser BCrypt "+user.getPassword());
            PrintWriter out = response.getWriter();
            Gson gson = new Gson();

            if (user != null) {
                // Generate JWT token if authentication is successful
                String token = JwtUtil.generateToken(user.getEmail(), user.getRole());
                System.out.println("authenticateUser BCrypt "+token);
                // Send the token as a response in a TokenResponse object
                response.setStatus(HttpServletResponse.SC_OK);  // 200 OK
                System.out.println("HttpServletResponse.SC_OK"+HttpServletResponse.SC_OK);
                out.print(gson.toJson(new TokenResponse(token)));  // Send token in response

            } else {
                // Invalid credentials
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);  // 401 Unauthorized
                System.out.println("HttpServletResponse.SC_OK"+HttpServletResponse.SC_UNAUTHORIZED);
                out.print(gson.toJson(new ErrorResponse("Invalid login credentials")));  // Error response
            }

            out.flush();

        } catch (Exception e) {
            e.printStackTrace();  // Log the error for debugging
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);  // 500 Internal Server Error
            System.out.println("HttpServletResponse.SC_INTERNAL_SERVER_ERROR"+HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\":\"An error occurred while processing your request\"}");
        }
    }
}
