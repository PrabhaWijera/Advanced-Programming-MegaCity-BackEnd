package com.prabhash.megacity.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.prabhash.megacity.dto.ReviewDTO;
import com.prabhash.megacity.service.ReviewService;
import com.prabhash.megacity.service.impl.ReviewServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/submitReview")
public class CustomerReview extends HttpServlet {
    private final ReviewService reviewService;

    public CustomerReview() {
        super();
        reviewService = new ReviewServiceImpl();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ReviewDTO reviewDTO = objectMapper.readValue(request.getInputStream(), ReviewDTO.class);

        boolean isSuccess = reviewService.submitReview(reviewDTO);

        if (isSuccess) {
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("{\"message\": \"Review submitted successfully!\"}");
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"Invalid review or rating.\"}");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<ReviewDTO> reviews = reviewService.getAllReviews();
        ObjectMapper objectMapper = new ObjectMapper();

        if (reviews != null && !reviews.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("application/json");
            response.getWriter().write(objectMapper.writeValueAsString(reviews));
        } else {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            response.getWriter().write("{\"message\": \"No reviews found.\"}");
        }
    }
}
