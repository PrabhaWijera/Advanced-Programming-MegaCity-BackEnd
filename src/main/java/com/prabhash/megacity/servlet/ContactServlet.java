package com.prabhash.megacity.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prabhash.megacity.dto.ContactDTO;

import com.prabhash.megacity.service.ContactService;
import com.prabhash.megacity.service.impl.ContactServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

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
}
