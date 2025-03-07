package com.prabhash.megacity.servlet;

import com.prabhash.megacity.dto.BookingDTO;
import com.prabhash.megacity.entity.Booking;
import com.prabhash.megacity.service.BookingService;
import com.prabhash.megacity.service.impl.BookingServiceImpl;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

@WebServlet("/booking/*")
public class BookingController extends HttpServlet {
    private BookingService bookingService = new BookingServiceImpl();
    private Gson gson = new Gson();

    // Handle CREATE Booking (POST)
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BufferedReader reader = req.getReader();
        BookingDTO bookingDTO = gson.fromJson(reader, BookingDTO.class);

        boolean success = bookingService.createBooking(bookingDTO);

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(gson.toJson(success ? "Booking Created Successfully" : "Failed to Create Booking"));
    }

    // Handle READ Booking by ID and ALL Bookings (GET)
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idParam = req.getParameter("id");

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        if (idParam != null) { // Get booking by ID
            int id = Integer.parseInt(idParam);
            Booking booking = bookingService.getBookingById(id);
            resp.getWriter().write(gson.toJson(booking));
        } else { // Get all bookings
            List<Booking> bookings = bookingService.getAllBookings();
            resp.getWriter().write(gson.toJson(bookings));
        }
    }

    // Handle UPDATE Booking (PUT)
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] urlParts = req.getRequestURI().split("/");
        String idParam = urlParts[urlParts.length - 1];

        if (idParam == null || idParam.isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Booking ID is required");
            return;
        }

        try {
            int id = Integer.parseInt(idParam);
            BufferedReader reader = req.getReader();
            BookingDTO bookingDTO = gson.fromJson(reader, BookingDTO.class);

            boolean success = bookingService.updateBooking(id, bookingDTO);

            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write(gson.toJson(success ? "Booking Updated Successfully" : "Failed to Update Booking"));
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Booking ID");
        }
    }




    // Handle DELETE Booking (DELETE)
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idParam = req.getParameter("id");
        System.out.println(idParam);
        if (idParam == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Booking ID is required");
            System.out.println("Booking ID is required");
            return;
        }

        int id = Integer.parseInt(idParam);
        boolean success = bookingService.deleteBooking(id);

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(gson.toJson(success ? "Booking Deleted Successfully" : "Failed to Delete Booking"));
    }
}
