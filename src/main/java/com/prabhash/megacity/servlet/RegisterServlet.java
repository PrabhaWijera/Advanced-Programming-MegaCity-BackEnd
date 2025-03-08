package com.prabhash.megacity.servlet;

import com.prabhash.megacity.config.ResponseMessage;
import com.prabhash.megacity.entity.User;
import com.prabhash.megacity.service.UserService;
import com.prabhash.megacity.service.WhatsAppMessageService;
import com.prabhash.megacity.service.impl.SmsServiceImpl;
import com.prabhash.megacity.service.impl.UserServiceImpl;
import com.prabhash.megacity.service.impl.WhatsAppMessageServiceImpl;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    UserService userService = new UserServiceImpl();
    WhatsAppMessageService messageService = new WhatsAppMessageServiceImpl();
    EmailService emailService = new EmailService();

    SmsServiceImpl smsService = new SmsServiceImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String roles=request.getParameter("role");



        emailService.sendTestEmail(email, " 🚕 You’re In! Welcome to MegaCity!", "Thank you for joining our amazing community. We’re thrilled to have you on board! 🍾");


        User user = new User(0, username, password, email, phone, roles);
        System.out.println(user.getRole());
        boolean isRegistered = userService.registerUser(user);

        PrintWriter out = response.getWriter();
        Gson gson = new Gson();

        if (isRegistered) {
            //normal sms
            //  smsService.sendSms(user.getPhone(), "Congratulations " + user.getUsername() + " on successfully registering!");
            //whatsapp
            messageService.sendRegistrationWhatsAppMessage(phone, username);
            response.setStatus(HttpServletResponse.SC_OK); // 200 OK
            ResponseMessage responseMessage = new ResponseMessage("Registration successful!");
            out.print(gson.toJson(responseMessage));
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400 Bad Request
            ResponseMessage responseMessage = new ResponseMessage("Failed to register.");
            out.print(gson.toJson(responseMessage));
        }

        out.flush();
    }
}
