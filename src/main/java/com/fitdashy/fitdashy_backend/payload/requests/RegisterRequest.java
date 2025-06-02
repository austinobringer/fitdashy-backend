package com.fitdashy.fitdashy_backend.payload.requests;

import java.util.HashSet;
import java.util.Set;

import jakarta.validation.constraints.*;

public class RegisterRequest {
    @NotBlank
    @Size(min = 3, max = 30)
    private String username;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank
    @Size(max = 50)
    private String fullname;

    @NotBlank
    @Size(min = 12, max = 64)
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFullname(String fullname) { this.fullname = fullname; }

    public String getFullname() {
        return fullname;
    }
}