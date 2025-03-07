package com.prabhash.megacity.servlet;

import com.google.gson.Gson;
import com.prabhash.megacity.dto.ContactDTO;
import com.prabhash.megacity.service.ContactService;
import com.prabhash.megacity.service.impl.ContactServiceImpl;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Logger;

@WebServlet("/contact")
public class ContactServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(ContactServlet.class.getName());
    private ContactService contactService;

    @Override
    public void init() throws ServletException {
        contactService = new ContactServiceImpl();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        try {
            // Read the request body (JSON)
            BufferedReader reader = request.getReader();
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            // Log the request payload (for debugging purposes)
            logger.info("Received payload: " + sb.toString());

            // Convert the JSON to a ContactDTO using Gson
            Gson gson = new Gson();
            ContactDTO contactDTO = gson.fromJson(sb.toString(), ContactDTO.class);

            // Log the ContactDTO (for debugging purposes)
            logger.info("Parsed ContactDTO: " + contactDTO);

            // Use the contact service to save the contact data
            contactService.saveContact(contactDTO);

            // Respond with success message
            response.setStatus(HttpServletResponse.SC_OK);
            out.write("{\"message\": \"Message sent successfully!\"}");
        } catch (Exception e) {
            // Log the error and provide feedback
            logger.severe("Error processing contact request: " + e.getMessage());
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.write("{\"error\": \"Error sending message.\"}");
        } finally {
            out.flush();
        }
    }
}
