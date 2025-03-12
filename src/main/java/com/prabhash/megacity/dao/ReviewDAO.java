package com.prabhash.megacity.dao;

import com.prabhash.megacity.entity.Review;
import java.util.List;

public interface ReviewDAO {
    boolean saveReview(Review review);
    List<Review> getAllReviews();
}
