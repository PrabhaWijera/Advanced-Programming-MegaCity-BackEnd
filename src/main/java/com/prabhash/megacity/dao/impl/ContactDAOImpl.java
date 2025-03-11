package com.prabhash.megacity.dao.impl;

import com.prabhash.megacity.config.DBConfig;
import com.prabhash.megacity.dao.ContactDAO;
import com.prabhash.megacity.dto.ContactDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ContactDAOImpl implements ContactDAO {

    @Override
    public void saveContact(ContactDTO contactDTO) throws Exception {
        String sql = "INSERT INTO contacts (name, email, message) VALUES (?, ?, ?)";

        try (Connection conn = DBConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, contactDTO.getName());
            stmt.setString(2, contactDTO.getEmail());
            stmt.setString(3, contactDTO.getMessage());
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new Exception("Database error: " + e.getMessage());
        }
    }
}
