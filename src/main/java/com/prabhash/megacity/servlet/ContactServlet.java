package com.prabhash.megacity.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.prabhash.megacity.dto.ContactDTO;

import com.prabhash.megacity.service.ContactService;
import com.prabhash.megacity.service.impl.ContactServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/contact")
public class ContactServlet extends HttpServlet {

    private ContactService contactService = new ContactServiceImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ContactDTO contactDTO = objectMapper.readValue(request.getInputStream(), ContactDTO.class);

        boolean isSuccess = contactService.saveContact(contactDTO);

        if (isSuccess) {
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("{\"message\": \"Contact submitted successfully!\"}");
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"Invalid contact data.\"}");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve all contacts via the service layer
        List<ContactDTO> contactDTOs = contactService.getAllContacts();

        // Convert the list of contactDTOs to JSON using Gson
        Gson gson = new Gson();
        String jsonResponse = gson.toJson(contactDTOs);

        // Set the response content type to JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Write the JSON response to the output stream
        PrintWriter out = response.getWriter();
        out.write(jsonResponse);
        out.flush();
    }
}
