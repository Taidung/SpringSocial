package vn.taidung.springsocial.model.request;

import jakarta.validation.constraints.NotBlank;

public class ActivateUserRequest {

    @NotBlank(message = "Token is required")
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
