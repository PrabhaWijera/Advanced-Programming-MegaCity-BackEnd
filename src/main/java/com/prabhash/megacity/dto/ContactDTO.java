package com.prabhash.megacity.dto;

public class ContactDTO {

    private Long id;
    private Long user_id;
    private String email;
    private String name;
    private String message;

    public ContactDTO() {
    }

    public ContactDTO(Long id, Long user_id, String email, String name, String message) {
        this.id = id;
        this.user_id = user_id;
        this.email = email;
        this.name = name;
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
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
