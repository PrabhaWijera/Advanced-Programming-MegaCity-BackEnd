package com.prabhash.megacity.service.impl;


import com.prabhash.megacity.dao.ReviewDAO;
import com.prabhash.megacity.dao.impl.ReviewDAOImpl;
import com.prabhash.megacity.dto.ReviewDTO;
import com.prabhash.megacity.entity.Review;
import com.prabhash.megacity.service.ReviewService;


import java.util.ArrayList;
import java.util.List;

public class ReviewServiceImpl implements ReviewService {
    private final ReviewDAO reviewDAO;

    public ReviewServiceImpl() {
        this.reviewDAO = new ReviewDAOImpl();
    }

    @Override
    public boolean submitReview(ReviewDTO reviewDTO) {
        // Validate the review data
        if (reviewDTO.getReviewText().isEmpty() || reviewDTO.getRating() < 1 || reviewDTO.getRating() > 5) {
            return false;
        }

        // Map DTO to Entity
        Review review = new Review();
        review.setUserId(String.valueOf(reviewDTO.getUserId()));
        review.setEmail(reviewDTO.getEmail());
        review.setReviewText(reviewDTO.getReviewText());
        review.setRating(reviewDTO.getRating());

        // Save review to database
        return reviewDAO.saveReview(review);
    }

    @Override
    public List<ReviewDTO> getAllReviews() {
        List<Review> reviews = reviewDAO.getAllReviews();
        List<ReviewDTO> reviewDTOs = new ArrayList<>();

        for (Review review : reviews) {
            ReviewDTO dto = new ReviewDTO();
            dto.setUserId(Integer.parseInt(review.getUserId()));
            dto.setEmail(review.getEmail());
            dto.setReviewText(review.getReviewText());
            dto.setRating(review.getRating());
            reviewDTOs.add(dto);
        }

        return reviewDTOs;
    }
}
