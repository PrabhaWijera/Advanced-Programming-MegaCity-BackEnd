package com.prabhash.megacity.service;

import com.prabhash.megacity.dto.ReviewDTO;
import java.util.List;

public interface ReviewService {
    boolean submitReview(ReviewDTO reviewDTO);
    List<ReviewDTO> getAllReviews();
}
