package com.prabhash.megacity.dao.impl;

import com.prabhash.megacity.config.DBConfig;
import com.prabhash.megacity.dao.ContactDAO;
import com.prabhash.megacity.entity.ContactEntity;

import java.sql.*;

public class ContactDAOImpl implements ContactDAO {

    @Override
    public boolean saveContact(ContactEntity contactEntity) {
        String query = "INSERT INTO contacts (user_id, name, email, message) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setLong(1, contactEntity.getUserId());
            stmt.setString(2, contactEntity.getName());
            stmt.setString(3, contactEntity.getEmail());
            stmt.setString(4, contactEntity.getMessage());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0; // Return true if the insertion was successful
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
