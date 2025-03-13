package com.prabhash.megacity.dao.impl;

import com.prabhash.megacity.config.DBConfig;
import com.prabhash.megacity.dao.ContactDAO;
import com.prabhash.megacity.entity.ContactEntity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

    @Override
    public List<ContactEntity> getAllContacts() {
        List<ContactEntity> contacts = new ArrayList<>();
        String query = "SELECT * FROM contacts"; // Define your SQL query

        try (Connection conn = DBConfig.getConnection();  // Assuming DBConfig is your database connection utility
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet resultSet = stmt.executeQuery()) { // Execute the query and get the result set

            // Iterate through the result set to retrieve each contact
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                Long userId = resultSet.getLong("user_id");
                String email = resultSet.getString("email");
                String name = resultSet.getString("name");
                String message = resultSet.getString("message");

                // Create a new ContactEntity object and add it to the list
                ContactEntity contactEntity = new ContactEntity(id, userId, email, name, message);
                contacts.add(contactEntity);
            }

        } catch (SQLException e) {
            e.printStackTrace();  // Handle SQL exceptions properly
        }

        return contacts;
    }

}
