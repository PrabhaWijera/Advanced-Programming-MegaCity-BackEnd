package com.prabhash.megacity.dao.impl;

import com.prabhash.megacity.config.DBConfig;
import com.prabhash.megacity.dao.ReviewDAO;
import com.prabhash.megacity.entity.Review;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReviewDAOImpl implements ReviewDAO {

    @Override
    public boolean saveReview(Review review) {
        String query = "INSERT INTO reviews (user_id, email, review_text, rating) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, review.getUserId());
            stmt.setString(2, review.getEmail());
            stmt.setString(3, review.getReviewText());
            stmt.setInt(4, review.getRating());

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Review> getAllReviews() {
        String query = "SELECT * FROM reviews";
        List<Review> reviews = new ArrayList<>();

        try (Connection conn = DBConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Review review = new Review();
                review.setId(rs.getInt("id"));
                review.setUserId(rs.getString("user_id"));
                review.setEmail(rs.getString("email"));
                review.setReviewText(rs.getString("review_text"));
                review.setRating(rs.getInt("rating"));
                review.setCreatedAt(rs.getTimestamp("created_at"));
                reviews.add(review);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reviews;
    }
}
