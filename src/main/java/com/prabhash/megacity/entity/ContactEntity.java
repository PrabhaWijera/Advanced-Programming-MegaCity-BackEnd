package com.prabhash.megacity.entity;

public class ContactEntity {

    private Long id; // Primary key
    private Long userId; // Foreign key to the user table
    private String email; // User's email
    private String name; // User's name
    private String message; // User's message

    public ContactEntity() {
    }

    // Constructor
    public ContactEntity(Long id, Long userId, String email, String name, String message) {
        this.id = id;
        this.userId = userId;
        this.email = email;
        this.name = name;
        this.message = message;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
